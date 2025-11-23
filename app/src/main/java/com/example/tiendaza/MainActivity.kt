package com.example.tiendaza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tiendaza.navigation.BottomBar
import com.example.tiendaza.navigation.BottomNavItem
import com.example.tiendaza.navigation.Routes
import com.example.tiendaza.ui.screens.DetailScreen
import com.example.tiendaza.ui.screens.HomeScreen
import com.example.tiendaza.ui.screens.ProfileScreen
import com.example.tiendaza.ui.screens.SearchScreen
import com.example.tiendaza.ui.screens.VenderScreen
import com.example.tiendaza.ui.theme.TiendazaTheme
import com.example.tiendaza.ui.viewmodel.MainViewModel
import com.example.tiendaza.ui.screens.CartScreen
import com.example.tiendaza.data.repository.PublicacionRepository
import com.example.tiendaza.ui.viewmodel.MainViewModelFactory



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Envuelve tu App en el tema
            TiendazaTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val bottomItems = listOf(
        BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Sell,
        BottomNavItem.Cart, BottomNavItem.Profile
    )

    // --- INYECCIÓN DE DEPENDENCIAS MANUAL ---
    // 1. Crea la instancia del Repository una sola vez.
    val publicacionRepository = PublicacionRepository()
    // 2. Crea la Factory que sabe cómo construir el ViewModel.
    val mainViewModelFactory = MainViewModelFactory(publicacionRepository)
    // 3. Pasa la factory a todas las llamadas de viewModel().
    //    El sistema se encargará de crear una única instancia del ViewModel.
    val mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)
    // -----------------------------------------

    Scaffold(
        bottomBar = { BottomBar(navController, bottomItems) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(viewModel = mainViewModel, onItemClick = { id ->
                    // --- SOLUCIÓN AQUÍ ---
                    // Convierte el 'id' (Long) a Int antes de navegar
                    navController.navigate(Routes.detailRoute(id.toInt()))
                })
            }

            composable(Routes.SEARCH) {
                SearchScreen(viewModel = mainViewModel, onItemClick = { id ->
                    // --- Y AQUÍ TAMBIÉN ---
                    navController.navigate(Routes.detailRoute(id.toInt()))
                })
            }
            composable(
                route = Routes.DETAIL,
                arguments = listOf(navArgument("publicacionId") { type = NavType.IntType })
            ) { backStackEntry ->
                // No es necesario obtener el ID aquí de esta forma si el ViewModel ya lo maneja,
                // pero si DetailScreen lo necesita directamente, está bien.
                val id = backStackEntry.arguments?.getInt("publicacionId") ?: -1
                DetailScreen(
                    publicacionId = id,
                    viewModel = mainViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.PROFILE) {
                ProfileScreen()
            }
            composable(Routes.SELL) {
                VenderScreen(
                    viewModel = mainViewModel,
                    onCreated = {
                        // Después de crear, volvemos al Home
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                    }
                )
            }

            composable(Routes.CART) {
                CartScreen()
            }
        }
    }
}

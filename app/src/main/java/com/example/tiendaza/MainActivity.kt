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
import com.example.tiendaza.ui.screens.CartScreen
import com.example.tiendaza.ui.screens.DetailScreen
import com.example.tiendaza.ui.screens.HomeScreen
import com.example.tiendaza.ui.screens.ProfileScreen
import com.example.tiendaza.ui.screens.SearchScreen
import com.example.tiendaza.ui.screens.VenderScreen
import com.example.tiendaza.ui.theme.TiendazaTheme
import com.example.tiendaza.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TiendazaTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()

    // ⭐ CORRECCIÓN: Declarar el ViewModel
    val mainViewModel: MainViewModel = viewModel()

    val bottomItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Sell,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = { BottomBar(navController, bottomItems) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    viewModel = mainViewModel,
                    onItemClick = { id: Long ->
                        navController.navigate(Routes.detailRoute(id.toInt()))
                    }
                )
            }

            composable(Routes.SEARCH) {
                SearchScreen(
                    viewModel = mainViewModel,
                    onItemClick = { id: Long ->
                        navController.navigate(Routes.detailRoute(id.toInt()))
                    }
                )
            }

            composable(
                route = Routes.DETAIL,
                arguments = listOf(navArgument("publicacionId") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("publicacionId") ?: -1
                DetailScreen(
                    publicacionId = id.toLong(),
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
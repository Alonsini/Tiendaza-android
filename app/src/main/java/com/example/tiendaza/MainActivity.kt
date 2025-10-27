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



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val bottomItems = listOf(BottomNavItem.Home,  BottomNavItem.Search, BottomNavItem.Sell,
        BottomNavItem.Cart, BottomNavItem.Profile)

    Scaffold(
        bottomBar = { BottomBar(navController, bottomItems) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding) // Usar el Modifier del framework
        ) {
            composable(Routes.HOME) {
                val vm: MainViewModel = viewModel()
                HomeScreen(viewModel = vm, onItemClick = { id ->
                    navController.navigate(Routes.detailRoute(id))
                })
            }

            composable(Routes.SEARCH) {
                val vm: MainViewModel = viewModel()
                SearchScreen(viewModel = vm, onItemClick = { id ->
                    navController.navigate(Routes.detailRoute(id))
                })
            }
            composable(
                route = Routes.DETAIL,
                arguments = listOf(navArgument("publicacionId") { type = NavType.IntType })
            ) { backStackEntry ->
                val vm: MainViewModel = viewModel()
                val id = backStackEntry.arguments?.getInt("publicacionId") ?: -1
                DetailScreen(publicacionId = id, viewModel = vm, onBack = { navController.popBackStack() })
            }

            composable(Routes.PROFILE) {
                ProfileScreen()
            }
            composable(Routes.SELL) {
                VenderScreen()
            }
            composable(Routes.CART){
                CartScreen()
            }
        }
    }
}

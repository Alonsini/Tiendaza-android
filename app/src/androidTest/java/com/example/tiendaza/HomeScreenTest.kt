package com.example.tiendaza

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiendaza.data.repository.FakePublicacionRepository
import com.example.tiendaza.ui.screens.HomeScreen
import com.example.tiendaza.ui.viewmodel.MainViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeScreen_muestraTituloYPublicacion() {
        // Usamos tu repo fake, que ya trae 2 productos de prueba
        val repo = FakePublicacionRepository()
        val viewModel = MainViewModel(repository = repo)

        composeRule.setContent {
            HomeScreen(
                viewModel = viewModel,
                onItemClick = {}
            )
        }

        // Comprueba que el título de la pantalla aparece
        composeRule.onNodeWithText("Publicaciones").assertIsDisplayed()

        // Y que aparece al menos uno de los productos del FakePublicacionRepository
        composeRule.onNodeWithText("Producto Test 1").assertIsDisplayed()
    }
}

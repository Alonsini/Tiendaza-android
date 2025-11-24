package com.example.tiendaza

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiendaza.ui.screens.CartScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun cartScreen_muestraTextoCarrito() {
        // When
        composeRule.setContent {
            CartScreen()
        }

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("Carrito").assertIsDisplayed()
        composeRule.onNodeWithText("Carrito próximamente").assertIsDisplayed()
    }

    @Test
    fun cartScreen_muestraTopBar() {
        // When
        composeRule.setContent {
            CartScreen()
        }

        composeRule.waitForIdle()

        // Then - La TopAppBar debe mostrar el título
        composeRule.onNodeWithText("Carrito").assertIsDisplayed()
    }

    @Test
    fun cartScreen_muestraMensajePlaceholder() {
        // When
        composeRule.setContent {
            CartScreen()
        }

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("Carrito próximamente").assertIsDisplayed()
    }
}
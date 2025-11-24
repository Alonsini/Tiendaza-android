package com.example.tiendaza

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiendaza.data.model.Publicacion
import com.example.tiendaza.data.repository.PublicacionRepository
import com.example.tiendaza.ui.screens.DetailScreen
import com.example.tiendaza.ui.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var repository: PublicacionRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun detailScreen_muestraDatosDeLaPublicacion() {
        // Given
        val fakeList = listOf(
            Publicacion(1, "Producto Detalle", "Descripción detalle", 5000, "imgd.jpg")
        )

        coEvery { repository.getAll() } returns fakeList

        val vm = MainViewModel(repository)

        // When
        composeRule.setContent {
            DetailScreen(
                publicacionId = 1L,
                viewModel = vm,
                onBack = {}
            )
        }

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("Detalle").assertIsDisplayed()
        composeRule.onNodeWithText("Producto Detalle").assertIsDisplayed()
        composeRule.onNodeWithText("Descripción detalle").assertIsDisplayed()
        composeRule.onNodeWithText("Precio: $5000").assertIsDisplayed()
    }

    @Test
    fun detailScreen_muestraBotonAgregarAlCarrito() {
        // Given
        val fakeList = listOf(
            Publicacion(1, "Producto Test", "Descripción", 10000, "img.jpg")
        )

        coEvery { repository.getAll() } returns fakeList

        val vm = MainViewModel(repository)

        // When
        composeRule.setContent {
            DetailScreen(
                publicacionId = 1L,
                viewModel = vm,
                onBack = {}
            )
        }

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("Agregar al Carrito").assertIsDisplayed()
    }

    @Test
    fun detailScreen_muestraBotonAtras() {
        // Given
        val fakeList = listOf(
            Publicacion(1, "Producto", "Desc", 1000, "img.jpg")
        )

        coEvery { repository.getAll() } returns fakeList

        val vm = MainViewModel(repository)

        // When
        composeRule.setContent {
            DetailScreen(
                publicacionId = 1L,
                viewModel = vm,
                onBack = {}
            )
        }

        composeRule.waitForIdle()

        // Then - El botón atrás debe existir (con contentDescription "Atrás")
        composeRule.onNodeWithContentDescription("Atrás").assertExists()
    }

    @Test
    fun detailScreen_botonAtrasEsFuncional() {
        // Given
        val fakeList = listOf(
            Publicacion(1, "Producto", "Desc", 1000, "img.jpg")
        )

        coEvery { repository.getAll() } returns fakeList

        val vm = MainViewModel(repository)
        var backPressed = false

        // When
        composeRule.setContent {
            DetailScreen(
                publicacionId = 1L,
                viewModel = vm,
                onBack = { backPressed = true }
            )
        }

        composeRule.waitForIdle()

        composeRule.onNodeWithContentDescription("Atrás").performClick()

        // Then
        assert(backPressed)
    }

    @Test
    fun detailScreen_muestraMensajeCuandoPublicacionNoExiste() {
        // Given
        val fakeList = listOf(
            Publicacion(1, "Producto", "Desc", 1000, "img.jpg")
        )

        coEvery { repository.getAll() } returns fakeList

        val vm = MainViewModel(repository)

        // When - Buscar una publicación con ID que no existe
        composeRule.setContent {
            DetailScreen(
                publicacionId = 999L,
                viewModel = vm,
                onBack = {}
            )
        }

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("La publicación no ha sido encontrada")
            .assertIsDisplayed()
    }

    @Test
    fun detailScreen_botonAgregarCarritoEsClickable() {
        // Given
        val fakeList = listOf(
            Publicacion(1, "Producto", "Desc", 1000, "img.jpg")
        )

        coEvery { repository.getAll() } returns fakeList

        val vm = MainViewModel(repository)

        // When
        composeRule.setContent {
            DetailScreen(
                publicacionId = 1L,
                viewModel = vm,
                onBack = {}
            )
        }

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("Agregar al Carrito")
            .assertExists()
            .assertHasClickAction()
    }
}
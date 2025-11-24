package com.example.tiendaza

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiendaza.data.model.Publicacion
import com.example.tiendaza.data.repository.PublicacionRepository
import com.example.tiendaza.ui.screens.VenderScreen
import com.example.tiendaza.ui.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VenderScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var repository: PublicacionRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        coEvery { repository.getAll() } returns emptyList()
        viewModel = MainViewModel(repository)
    }

    @Test
    fun venderScreen_muestraCamposBasicos() {
        // When
        composeRule.setContent {
            VenderScreen(viewModel = viewModel)
        }

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("Publicar un Producto").assertExists()
        composeRule.onNodeWithText("Nombre del Producto").assertExists()
        composeRule.onNodeWithText("Descripción").assertExists()
        composeRule.onNodeWithText("Precio").assertExists()
        composeRule.onNodeWithText("📷 Tomar Foto").assertExists()
        composeRule.onNodeWithText("🖼️ Subir Imagen").assertExists()
        composeRule.onNodeWithText("Vista Previa:").assertExists()
        composeRule.onNodeWithText("PUBLICAR").assertExists()
    }

    @Test
    fun venderScreen_permiteIngresarTexto() {
        // When
        composeRule.setContent {
            VenderScreen(viewModel = viewModel)
        }

        composeRule.waitForIdle()

        // Ingresar texto en los campos
        composeRule.onNodeWithText("Nombre del Producto")
            .performTextInput("Polera Azul")

        composeRule.onNodeWithText("Descripción")
            .performTextInput("Polera talla M, algodón")

        composeRule.onNodeWithText("Precio")
            .performTextInput("15000")

        // Then - Los textos deben aparecer
        composeRule.onNodeWithText("Polera Azul").assertIsDisplayed()
        composeRule.onNodeWithText("Polera talla M, algodón").assertIsDisplayed()
        composeRule.onNodeWithText("15000").assertIsDisplayed()
    }

    @Test
    fun venderScreen_validaCamposVaciosAlPublicar() {
        // When
        composeRule.setContent {
            VenderScreen(viewModel = viewModel)
        }

        composeRule.waitForIdle()

        // Intenta publicar sin llenar campos
        composeRule.onNodeWithText("PUBLICAR").performClick()

        composeRule.waitForIdle()

        // Then - Debe mostrar mensaje de error (capturado por el Snackbar)
        composeRule.onNodeWithText("Completa todos los campos")
            .assertIsDisplayed()
    }

    @Test
    fun venderScreen_validaPrecioNumerico() {
        // When
        composeRule.setContent {
            VenderScreen(viewModel = viewModel)
        }

        composeRule.waitForIdle()

        composeRule.onNodeWithText("Nombre del Producto")
            .performTextInput("Producto Test")

        composeRule.onNodeWithText("Descripción")
            .performTextInput("Descripción test")

        composeRule.onNodeWithText("Precio")
            .performTextInput("abc") // Texto en lugar de número

        composeRule.onNodeWithText("PUBLICAR").performClick()

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("Precio inválido")
            .assertIsDisplayed()
    }

    @Test
    fun venderScreen_validaImagenRequerida() {
        // When
        composeRule.setContent {
            VenderScreen(viewModel = viewModel)
        }

        composeRule.waitForIdle()

        composeRule.onNodeWithText("Nombre del Producto")
            .performTextInput("Zapatillas Nike")

        composeRule.onNodeWithText("Descripción")
            .performTextInput("Talla 42")

        composeRule.onNodeWithText("Precio")
            .performTextInput("89000")

        // No seleccionar imagen

        composeRule.onNodeWithText("PUBLICAR").performClick()

        composeRule.waitForIdle()

        // Then - Debe pedir seleccionar imagen
        composeRule.onNodeWithText("Selecciona una imagen de la galería")
            .assertIsDisplayed()
    }

    @Test
    fun venderScreen_muestraBotonesDeImagen() {
        // When
        composeRule.setContent {
            VenderScreen(viewModel = viewModel)
        }

        composeRule.waitForIdle()

        // Then - Ambos botones deben estar presentes
        composeRule.onNodeWithText("📷 Tomar Foto").assertIsDisplayed()
        composeRule.onNodeWithText("🖼️ Subir Imagen").assertIsDisplayed()
    }

    @Test
    fun venderScreen_muestraVistaPrevia() {
        // When
        composeRule.setContent {
            VenderScreen(viewModel = viewModel)
        }

        composeRule.waitForIdle()

        // Then - Debe mostrar el mensaje de ninguna imagen
        composeRule.onNodeWithText("Ninguna imagen seleccionada")
            .assertIsDisplayed()
    }

    @Test
    fun venderScreen_todosLosCamposPresentes() {
        // When
        composeRule.setContent {
            VenderScreen(viewModel = viewModel)
        }

        composeRule.waitForIdle()

        // Then - Verificar que todos los elementos están presentes
        composeRule.onNode(hasText("Nombre del Producto")).assertExists()
        composeRule.onNode(hasText("Descripción")).assertExists()
        composeRule.onNode(hasText("Precio")).assertExists()
        composeRule.onNode(hasText("Vista Previa:")).assertExists()
        composeRule.onNode(hasText("PUBLICAR")).assertExists()
    }
}
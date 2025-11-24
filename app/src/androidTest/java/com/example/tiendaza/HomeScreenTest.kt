package com.example.tiendaza

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiendaza.data.model.Publicacion
import com.example.tiendaza.data.repository.PublicacionRepository
import com.example.tiendaza.ui.screens.HomeScreen
import com.example.tiendaza.ui.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var repository: PublicacionRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun homeScreen_muestraListaDePublicaciones() {
        // Given - Arrange
        val fakeList = listOf(
            Publicacion(1, "Producto 1", "Desc 1", 1000, "img1.jpg"),
            Publicacion(2, "Producto 2", "Desc 2", 2000, "img2.jpg")
        )

        coEvery { repository.getAll() } returns fakeList

        // When - Act
        composeRule.setContent {
            HomeScreen(viewModel = viewModel, onItemClick = {})
        }

        // Esperar a que cargue
        composeRule.waitForIdle()

        // Then - Assert
        composeRule.onNodeWithText("Publicaciones").assertIsDisplayed()
        composeRule.onNodeWithText("Producto 1").assertIsDisplayed()
        composeRule.onNodeWithText("Producto 2").assertIsDisplayed()
    }

    @Test
    fun homeScreen_muestraEstadoDeLoading() {
        // Given
        coEvery { repository.getAll() } coAnswers {
            delay(5000) // Simula carga lenta
            emptyList()
        }

        // When
        composeRule.setContent {
            HomeScreen(viewModel = viewModel, onItemClick = {})
        }

        // Then - Verifica que se muestra el indicador de carga
        composeRule.onNodeWithText("Cargando publicaciones...").assertIsDisplayed()
    }

    @Test
    fun homeScreen_clickEnProductoNavegaAlDetalle() {
        // Given
        val fakeList = listOf(
            Publicacion(1, "Laptop HP", "16GB RAM", 899000, "laptop.jpg")
        )

        coEvery { repository.getAll() } returns fakeList

        var clickedId: Long? = null

        // When
        composeRule.setContent {
            HomeScreen(
                viewModel = viewModel,
                onItemClick = { id -> clickedId = id }
            )
        }

        composeRule.waitForIdle()

        composeRule.onNodeWithText("Ver detalles").performClick()

        // Then
        assert(clickedId == 1L)
    }

    @Test
    fun homeScreen_muestraListaVaciaCuandoNoHayPublicaciones() {
        // Given
        coEvery { repository.getAll() } returns emptyList()

        // When
        composeRule.setContent {
            HomeScreen(viewModel = viewModel, onItemClick = {})
        }

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("No hay publicaciones disponibles")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_muestraBotónDeRecarga() {
        // Given
        val fakeList = listOf(
            Publicacion(1, "Producto Test", "Desc Test", 1000, "img.jpg")
        )

        coEvery { repository.getAll() } returns fakeList

        // When
        composeRule.setContent {
            HomeScreen(viewModel = viewModel, onItemClick = {})
        }

        composeRule.waitForIdle()

        // Then - El botón de recarga (🔄) debe estar visible
        composeRule.onNodeWithText("🔄").assertIsDisplayed()
    }
}
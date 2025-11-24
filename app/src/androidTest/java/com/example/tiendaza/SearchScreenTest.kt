package com.example.tiendaza

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiendaza.data.model.Publicacion
import com.example.tiendaza.data.repository.PublicacionRepository
import com.example.tiendaza.ui.screens.SearchScreen
import com.example.tiendaza.ui.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

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
    fun searchScreen_muestraCampoDeBusqueda() {
        // Given
        coEvery { repository.getAll() } returns emptyList()

        // When
        composeRule.setContent {
            SearchScreen(viewModel = viewModel, onItemClick = {})
        }

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("Buscar publicaciones").assertIsDisplayed()
        composeRule.onNodeWithText("Ingresa un nombre para buscar").assertIsDisplayed()
        composeRule.onNodeWithText("🔍").assertIsDisplayed()
    }

    @Test
    fun searchScreen_permiteIngresarTextoDeBusqueda() {
        // Given
        coEvery { repository.getAll() } returns emptyList()
        coEvery { repository.search(any()) } returns emptyList()

        // When
        composeRule.setContent {
            SearchScreen(viewModel = viewModel, onItemClick = {})
        }

        composeRule.waitForIdle()

        // Ingresar texto en el campo de búsqueda
        composeRule.onNodeWithText("Ingresa un nombre para buscar")
            .performTextInput("Laptop")

        // Then
        composeRule.onNodeWithText("Laptop").assertIsDisplayed()
    }

    @Test
    fun searchScreen_realizaBusquedaAlPresionarBoton() {
        // Given
        val resultadosBusqueda = listOf(
            Publicacion(1, "Laptop HP", "Core i7", 899000, "laptop.jpg"),
            Publicacion(2, "Laptop Dell", "Core i5", 799000, "laptop2.jpg")
        )

        coEvery { repository.getAll() } returns emptyList()
        coEvery { repository.search("Laptop") } returns resultadosBusqueda

        // When
        composeRule.setContent {
            SearchScreen(viewModel = viewModel, onItemClick = {})
        }

        composeRule.waitForIdle()

        composeRule.onNodeWithText("Ingresa un nombre para buscar")
            .performTextInput("Laptop")

        composeRule.onNodeWithText("🔍").performClick()

        composeRule.waitForIdle()

        // Then - Debe mostrar los resultados
        composeRule.onNodeWithText("Laptop HP").assertIsDisplayed()
        composeRule.onNodeWithText("Laptop Dell").assertIsDisplayed()
    }

    @Test
    fun searchScreen_muestraMensajeCuandoNoHayResultados() {
        // Given
        coEvery { repository.getAll() } returns emptyList()
        coEvery { repository.search(any()) } returns emptyList()

        // When
        composeRule.setContent {
            SearchScreen(viewModel = viewModel, onItemClick = {})
        }

        composeRule.waitForIdle()

        composeRule.onNodeWithText("Ingresa un nombre para buscar")
            .performTextInput("ProductoInexistente")

        composeRule.onNodeWithText("🔍").performClick()

        composeRule.waitForIdle()

        // Then
        composeRule.onNodeWithText("No se encontraron resultados").assertIsDisplayed()
    }

    @Test
    fun searchScreen_clickEnResultadoNavegaAlDetalle() {
        // Given
        val resultadosBusqueda = listOf(
            Publicacion(1, "Manzana", "Fruta fresca", 1000, "manzana.jpg")
        )

        coEvery { repository.getAll() } returns emptyList()
        coEvery { repository.search("Manzana") } returns resultadosBusqueda

        var clickedId: Long? = null

        // When
        composeRule.setContent {
            SearchScreen(
                viewModel = viewModel,
                onItemClick = { id -> clickedId = id }
            )
        }

        composeRule.waitForIdle()

        composeRule.onNodeWithText("Ingresa un nombre para buscar")
            .performTextInput("Manzana")

        composeRule.onNodeWithText("🔍").performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithText("Ver detalles").performClick()

        // Then
        assert(clickedId == 1L)
    }
}
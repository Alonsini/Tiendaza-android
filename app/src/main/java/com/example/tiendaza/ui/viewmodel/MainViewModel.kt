package com.example.tiendaza.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiendaza.data.model.Publicacion
import com.example.tiendaza.data.repository.PublicacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainViewModel(
    private val repo: PublicacionRepository
) : ViewModel() {

    private val _publicaciones = MutableStateFlow<List<Publicacion>>(emptyList())
    val items = _publicaciones.asStateFlow()

    init {
        loadPublicaciones()
    }

    private fun loadPublicaciones() {
        viewModelScope.launch {
            try {
                _publicaciones.value = repo.getAll()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun refresh() {
        loadPublicaciones()
    }

    fun getPublicacion(id: Long): Publicacion? {
        return _publicaciones.value.find { it.id == id }
    }

    // NUEVO: crear con imagen
    fun crearPublicacionConImagen(
        image: MultipartBody.Part,
        titulo: RequestBody,
        descripcion: RequestBody,
        precio: RequestBody,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repo.createWithImage(image, titulo, descripcion, precio)
                loadPublicaciones()
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e.message ?: "Error al crear publicación")
            }
        }
    }
}

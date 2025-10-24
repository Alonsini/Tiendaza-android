package com.example.tiendaza.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiendaza.data.model.Publicacion

import com.example.tiendaza.data.repository.PublicacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: PublicacionRepository = PublicacionRepository()
) : ViewModel() {

    private val _publicaciones = MutableStateFlow<List<Publicacion>>(emptyList())
    val items: StateFlow<List<Publicacion>> = _publicaciones.asStateFlow()

    init {
        viewModelScope.launch {
            _publicaciones.value = repo.getAll()
        }
    }

    fun getPublicacion(id: Int): Publicacion? = repo.getById(id)
}

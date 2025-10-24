package com.example.tiendaza.data.repository

import com.example.tiendaza.data.model.Publicacion
import kotlin.collections.find


class PublicacionRepository {


    private val items = mutableListOf<Publicacion>().apply {
        add(Publicacion(1, "Cámara Nikon", "Cámara DSLR"))
        add(Publicacion(2, "Teléfono Samsung", "Pantalla AMOLED"))
        add(Publicacion(3, "Tablet Lenovo", "Pantalla 10 pulgadas"))
        add(Publicacion(4, "Audífonos JBL", "Bluetooth"))
        add(Publicacion(5, "Monitor LG", "4K UltraHD"))
    }


    fun getAll(): List<Publicacion> = items
    fun getById(id: Int): Publicacion? = items.find { it.id == id }
}
package com.example.tiendaza.data.repository

import com.example.tiendaza.data.model.Publicacion
import com.example.tiendaza.data.remote.ApiClient
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PublicacionRepository {

    private val api = ApiClient.service

    suspend fun getAll(): List<Publicacion> = api.getPublicaciones()

    suspend fun getById(id: Long): Publicacion = api.getPublicacionPorId(id)

    suspend fun search(query: String?): List<Publicacion> = api.search(query)

    suspend fun create(publicacion: Publicacion): Publicacion = api.create(publicacion)

    // NUEVO: crear con imagen
    suspend fun createWithImage(
        image: MultipartBody.Part,
        titulo: RequestBody,
        descripcion: RequestBody,
        precio: RequestBody
    ): Publicacion = api.createWithImage(image, titulo, descripcion, precio)

    suspend fun update(id: Long, pub: Publicacion): Publicacion = api.update(id, pub)

    suspend fun delete(id: Long) = api.delete(id)
}

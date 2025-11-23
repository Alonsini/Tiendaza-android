package com.example.tiendaza.data.remote

import com.example.tiendaza.data.model.Publicacion
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface TiendazaApiService {

    // LISTAR TODAS
    @GET("api/publicaciones")
    suspend fun getPublicaciones(): List<Publicacion>

    // OBTENER POR ID
    @GET("api/publicaciones/{id}")
    suspend fun getPublicacionPorId(
        @Path("id") id: Long
    ): Publicacion

    // BUSCAR
    @GET("api/publicaciones/search")
    suspend fun search(
        @Query("query") query: String?
    ): List<Publicacion>

    // CREAR (JSON normal, por si lo quieres usar)
    @POST("api/publicaciones")
    suspend fun create(
        @Body publicacion: Publicacion
    ): Publicacion

    // CREAR CON IMAGEN (multipart) – NUEVO
    @Multipart
    @POST("api/publicaciones/con-imagen")
    suspend fun createWithImage(
        @Part image: MultipartBody.Part,
        @Part("titulo") titulo: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part("precio") precio: RequestBody
    ): Publicacion

    // ACTUALIZAR
    @PUT("api/publicaciones/{id}")
    suspend fun update(
        @Path("id") id: Long,
        @Body data: Publicacion
    ): Publicacion

    // ELIMINAR
    @DELETE("api/publicaciones/{id}")
    suspend fun delete(@Path("id") id: Long)
}

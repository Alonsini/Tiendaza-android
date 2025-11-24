package com.example.tiendaza.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")

    inputStream?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }

    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", file.name, requestBody)
}

fun String.toPlainTextRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}
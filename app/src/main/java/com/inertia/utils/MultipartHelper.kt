package com.inertia.utils

import android.net.Uri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object MultipartHelper {
    fun getPart(uri: Uri): MultipartBody.Part {
        val file = File(uri.path)
        return MultipartBody.Part.createFormData("foto", file.name, RequestBody.create("image/*".toMediaTypeOrNull(), file))
    }
}
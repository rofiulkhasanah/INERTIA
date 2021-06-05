package com.inertia.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object MultipartHelper {
    fun getPart(file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            "foto",
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )
    }
}
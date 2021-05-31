package com.inertia.utils

import android.net.Uri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object MultipartHelper {
    fun getPart(file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData("foto", file.name, RequestBody.create("image/*".toMediaTypeOrNull(), file))
    }
}
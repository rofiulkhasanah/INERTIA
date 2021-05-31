package com.inertia.data.datasource.remote.request

import android.net.Uri
import okhttp3.MultipartBody
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File

data class BencanaRequest(
    var file: File,
    val judul: String,
    val kronologi: String,
    val nomor_wa: String,
    val waktu_bencana: String,
    val lat_long: String
)

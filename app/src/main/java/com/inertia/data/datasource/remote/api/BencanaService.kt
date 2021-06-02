package com.inertia.data.datasource.remote.api

import com.inertia.data.datasource.remote.response.BencanaResponse
import com.inertia.data.datasource.remote.response.LaporResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface BencanaService {
    @GET("report/show")
    fun getAllBencana(): Call<BencanaResponse>

    @GET("report/get/{nomor_wa}")
    fun getBencanaByNomorWa(@Path("nomor_wa") nomorWa: String): Call<BencanaResponse>

    @Multipart
    @POST("report/add")
    fun createLaporan(
        @Part filePart: MultipartBody.Part,
        @Query("judul") judul: String,
        @Query("kronologi") kronologi: String,
        @Query("nomor_wa") nomor_wa: String,
        @Query("waktu_bencana") waktu_bencana: String,
        @Query("lat_long") lat_long: String
    ): Call<LaporResponse>

    @POST("report/update/{id_aduan}")
    fun editDonasiUri(
        @Path("id_aduan") idAduan: String,
        @Path("url") donasiUri: String)
}
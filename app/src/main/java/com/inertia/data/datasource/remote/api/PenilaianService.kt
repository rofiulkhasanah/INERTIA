package com.inertia.data.datasource.remote.api

import com.inertia.data.datasource.remote.response.PenilaianResponse
import com.inertia.data.datasource.remote.response.StoreFormPenilaianResponse
import com.inertia.data.datasource.remote.response.TerdampakResponse
import com.inertia.data.response.JenisBencanaResponse
import com.inertia.data.response.SkalaResponse
import com.inertia.data.response.SubSektorResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PenilaianService {
    @GET("/skalas")
    fun getSkalas(): Call<List<SkalaResponse>>

    @GET("/jenisbencanas")
    fun getJenisBencanas(): Call<List<JenisBencanaResponse>>

    @GET("/subsektors")
    fun getSubSektors(): Call<List<SubSektorResponse>>

    @POST("/storeformpenilaian")
    fun storeFormPenilaian(
        @Query("nomorWa") username: String?,
        @Query("nmBencana") nmBencana: String?,
        @Query("idSub") idSub: Int?,
        @Query("name") name: String?,
        @Query("alamat") alamat: String?,
        @Query("provinsi") provinsi: String?,
        @Query("kota") kota: String?,
        @Query("tanggal") tanggal: String?,
        @Query("penilaian") penilaian: String?,
    ): Call<StoreFormPenilaianResponse>

    @GET("/multimoora4")
    fun getPenilaian(
        @Query("idKasus") idKasus: String?,
    ): Call<PenilaianResponse>
}
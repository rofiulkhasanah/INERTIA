package com.inertia.data.datasource.remote.api

import com.inertia.data.response.JenisBencanaResponse
import com.inertia.data.response.SkalaResponse
import com.inertia.data.response.SubSektorResponse
import retrofit2.Call
import retrofit2.http.GET

interface PenilaianService {
    @GET("/skalas")
    fun getSkalas(): Call<List<SkalaResponse>>

    @GET("/jenisbencanas")
    fun getJenisBencanas(): Call<List<JenisBencanaResponse>>

    @GET("/subsektors")
    fun getSubSektors(): Call<List<SubSektorResponse>>
}
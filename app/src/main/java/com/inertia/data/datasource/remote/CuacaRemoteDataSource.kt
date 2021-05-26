package com.inertia.data.datasource.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.remote.api.BencanaService
import com.inertia.data.datasource.remote.api.CuacaService
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuacaRemoteDataSource(private val service: CuacaService) {

    companion object {
        @Volatile
        private var instance: CuacaRemoteDataSource? = null

        fun getInstance(service: CuacaService): CuacaRemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: CuacaRemoteDataSource(service).apply {
                    instance = this
                }
            }
    }

    fun getCuaca(latitude: String, longitude: String) = service.getCuaca(latitude, longitude)
}
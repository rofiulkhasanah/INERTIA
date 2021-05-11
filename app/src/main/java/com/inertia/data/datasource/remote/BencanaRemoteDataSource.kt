package com.inertia.data.datasource.remote

import com.inertia.data.api.InertiaService
import com.inertia.data.response.BencanaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BencanaRemoteDataSource private constructor(private val inertiaService: InertiaService){
    companion object {
        @Volatile
        private var instance: BencanaRemoteDataSource? = null

        fun getInstance(inertiaService: InertiaService): BencanaRemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: BencanaRemoteDataSource(inertiaService).apply {
                    instance = this
                }
        }
    }

    fun getAllBencana(callback: LoadAllBencanaCallback) {
        inertiaService.getBencanaService().getAllBencana().enqueue(object : Callback<List<BencanaResponse>> {
            override fun onResponse(
                call: Call<List<BencanaResponse>>,
                response: Response<List<BencanaResponse>>
            ) {
                val data = response.body()
                if (data != null) {
                    callback.onLoadAllBencanaCallback(data)
                }
            }

            override fun onFailure(call: Call<List<BencanaResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    interface LoadAllBencanaCallback {
        fun onLoadAllBencanaCallback(response: List<BencanaResponse>)
    }
}
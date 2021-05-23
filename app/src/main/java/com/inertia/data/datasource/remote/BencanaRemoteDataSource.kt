package com.inertia.data.datasource.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.remote.api.BencanaService
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.BencanaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BencanaRemoteDataSource private constructor(private val service: BencanaService){
    companion object {
        @Volatile
        private var instance: BencanaRemoteDataSource? = null

        fun getInstance(service: BencanaService): BencanaRemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: BencanaRemoteDataSource(service).apply {
                    instance = this
                }
        }
    }

    fun getAllBencana(): LiveData<ApiResponse<List<BencanaResponse>>> {
        val listBencana = MutableLiveData<ApiResponse<List<BencanaResponse>>>()
        service.getAllBencana().enqueue(object : Callback<List<BencanaResponse>> {
            override fun onResponse(
                call: Call<List<BencanaResponse>>,
                response: Response<List<BencanaResponse>>
            ) {
                val data = response.body()
                Log.d("getAllBencana", "Sudah sampe sini lho")
                if (data != null) {
                    val bencanaResponse = ApiResponse.success(data)
                    listBencana.postValue(bencanaResponse)
                }
            }

            override fun onFailure(call: Call<List<BencanaResponse>>, t: Throwable) {
                Log.e("BencanaRemoteDataSource", "Error: ${t.message}")
            }

        })
        return listBencana
    }
}
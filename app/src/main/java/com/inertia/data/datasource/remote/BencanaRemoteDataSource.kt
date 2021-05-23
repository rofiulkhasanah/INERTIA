package com.inertia.data.datasource.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.remote.api.InertiaService
import com.inertia.data.datasource.remote.response.BencanaResponse
import com.inertia.data.datasource.remote.response.ApiResponse
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

    fun getAllBencana(): LiveData<ApiResponse<List<BencanaResponse>>> {
        val listBencana = MutableLiveData<ApiResponse<List<BencanaResponse>>>()
        inertiaService.getBencanaService().getAllBencana().enqueue(object : Callback<List<BencanaResponse>> {
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
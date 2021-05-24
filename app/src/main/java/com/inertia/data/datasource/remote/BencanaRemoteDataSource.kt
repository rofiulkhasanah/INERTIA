package com.inertia.data.datasource.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.remote.api.BencanaService
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.BencanaItem
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

    fun getAllBencana(): LiveData<ApiResponse<List<BencanaItem>>> {
        val mutableListBencana = MutableLiveData<ApiResponse<List<BencanaItem>>>()
        service.getAllBencana().enqueue(object : Callback<BencanaResponse> {
            override fun onResponse(
                call: Call<BencanaResponse>,
                response: Response<BencanaResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        val listBencana = ApiResponse.success(data.result)
                        mutableListBencana.postValue(listBencana)
                    }else{
                        ApiResponse.empty(response.message(), null)
                    }
                }else{
                    ApiResponse.error(response.message(), null)
                    Log.e("GetBencana", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BencanaResponse>, t: Throwable) {
                Log.e("GetBencana", "Error: ${t.message}")
                ApiResponse.error(t.message, null)
            }

        })
        return mutableListBencana
    }
}
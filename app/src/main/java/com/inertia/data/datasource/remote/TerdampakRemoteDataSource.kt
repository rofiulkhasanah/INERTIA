package com.inertia.data.datasource.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.remote.api.TerdampakService
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.TerdampakResponse
import com.inertia.data.datasource.remote.response.TerdampakResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TerdampakRemoteDataSource(private val service: TerdampakService) {

    companion object {
        @Volatile
        private var instance: TerdampakRemoteDataSource? = null
        fun getInstance(service: TerdampakService): TerdampakRemoteDataSource =
            instance ?: synchronized(this){
                instance ?: TerdampakRemoteDataSource(service).apply {
                    instance = this
                }
            }
    }



    fun getAllTerdampak(nomorWa: String): LiveData<ApiResponse<List<TerdampakResponseItem>>> {
        val mutableListTerdampak = MutableLiveData<ApiResponse<List<TerdampakResponseItem>>>()
        service.getAllTerdampak(nomorWa).enqueue(object : Callback<TerdampakResponse> {
            override fun onResponse(
                call: Call<TerdampakResponse>,
                response: Response<TerdampakResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if(data.terdampakResponse.isNotEmpty()){
                            val listTerdampak = ApiResponse.success(data.terdampakResponse)
                            mutableListTerdampak.postValue(listTerdampak)
                        }else{
                            val result = ApiResponse.empty(response.message(), data.terdampakResponse)
                            mutableListTerdampak.postValue(result)
                        }
                    }
                }else{
                    Log.e("GetData", "onError: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TerdampakResponse>, t: Throwable) {
                Log.e("GetData", "onFailure: ${t.message}")
            }

        })
        return mutableListTerdampak
    }
}
package com.inertia.data.datasource.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.remote.api.BencanaService
import com.inertia.data.datasource.remote.request.BencanaRequest
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.BencanaItem
import com.inertia.data.datasource.remote.response.BencanaResponse
import com.inertia.utils.DummyData
import com.inertia.utils.MultipartHelper
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
                        if (data.result != null && data.result.isNotEmpty()) {
                            val listBencana = ApiResponse.success(data.result)
                            mutableListBencana.postValue(listBencana)
                        }else{
                            val result = ApiResponse.empty(response.message(), DummyData.listBencana)
                            mutableListBencana.postValue(result)
                        }
                    }
                }else{
                    mutableListBencana.postValue(ApiResponse.error(response.message(), DummyData.listBencana))
                    Log.e("GetBencana", "onError: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BencanaResponse>, t: Throwable) {
                Log.e("GetBencana", "onFailure: ${t.message}")
                mutableListBencana.postValue(ApiResponse.error(t.message, DummyData.listBencana))
            }

        })
        return mutableListBencana
    }

    fun createLaporan(request: BencanaRequest): LiveData<BencanaResponse> {

        val liveDataResponse = MutableLiveData<BencanaResponse>()
        val foto = MultipartHelper.getPart(request.imageUri)
        service.createLaporan(judul = request.judul, kronologi = request.kronologi,
            lat_long = request.lat_long, nomor_wa = request.nomor_wa,
            waktu_bencana = request.waktu_bencana, filePart = foto).enqueue(object : Callback<BencanaResponse> {
            override fun onResponse(
                call: Call<BencanaResponse>,
                response: Response<BencanaResponse>
            ) {
               if (response.isSuccessful) {
                   val data = response.body()
                   if (data?.result != null && data.result.isNotEmpty()) {
                       liveDataResponse.postValue(data)
                   }
               }else{
                   Log.e("BencanaRemoteDataSource", response.message())
               }
            }

            override fun onFailure(call: Call<BencanaResponse>, t: Throwable) {
                t.message?.let { Log.e("BencanaRemoteDataSource", it) }
            }

        })
        return liveDataResponse
    }
}
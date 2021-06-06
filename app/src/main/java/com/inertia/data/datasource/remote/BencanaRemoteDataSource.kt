package com.inertia.data.datasource.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.remote.api.BencanaService
import com.inertia.data.datasource.remote.request.BencanaRequest
import com.inertia.data.datasource.remote.response.*
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
                        if (data.bencana != null && data.bencana.isNotEmpty()) {
                            val listBencana = ApiResponse.success(data.bencana)
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

    fun getBencanaByNomorWa(nomorWa: String): LiveData<ApiResponse<List<BencanaItem>>> {
        val mutableListBencana = MutableLiveData<ApiResponse<List<BencanaItem>>>()
        service.getBencanaByNomorWa(nomorWa).enqueue(object : Callback<BencanaResponse> {
            override fun onResponse(
                call: Call<BencanaResponse>,
                response: Response<BencanaResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.bencana != null && data.bencana.isNotEmpty()) {
                            val listBencana = ApiResponse.success(data.bencana)
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

    fun createLaporan(request: BencanaRequest): LiveData<ApiResponse<LaporResponse>> {

        val liveDataResponse = MutableLiveData<ApiResponse<LaporResponse>>()
        val foto = MultipartHelper.getPart(request.file)
        service.createLaporan(judul = request.judul, kronologi = request.kronologi,
            lat_long = request.lat_long, nomor_wa = request.nomor_wa,
            waktu_bencana = request.waktu_bencana, filePart = foto).enqueue(object : Callback<LaporResponse> {
            override fun onResponse(call: Call<LaporResponse>, response: Response<LaporResponse>) {
               if (response.isSuccessful) {
                   val data = response.body()
                   if (data != null) {
                       liveDataResponse.postValue(ApiResponse.success(data))
                   }
               }else{
                   liveDataResponse.postValue(ApiResponse.error(response.message(), LaporResponse()))
                   Log.e("BencanaRemoteDataSource", response.message())
               }
            }

            override fun onFailure(call: Call<LaporResponse>, t: Throwable) {
                t.message?.let {
                    Log.e("BencanaRemoteDataSource", it)
                    liveDataResponse.postValue(ApiResponse.error(it, LaporResponse()))
                }
            }

        })
        return liveDataResponse
    }

    fun updateUriDonasi(idAduan: String, uriDonasi: String): ApiResponse<UpdateBencanaResponse>? {
        var result: ApiResponse<UpdateBencanaResponse>? = null
        service.editDonasiUri(idAduan, uriDonasi).enqueue(object : Callback<UpdateBencanaResponse> {
            override fun onResponse(
                call: Call<UpdateBencanaResponse>,
                response: Response<UpdateBencanaResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        result =  ApiResponse.success(data)
                    }
                }else{
                    result = ApiResponse.error(response.message(), UpdateBencanaResponse(idAduan))
                }
            }

            override fun onFailure(call: Call<UpdateBencanaResponse>, t: Throwable) {
                result = ApiResponse.error(t.message, UpdateBencanaResponse(idAduan))
            }

        })
        return result
    }
}
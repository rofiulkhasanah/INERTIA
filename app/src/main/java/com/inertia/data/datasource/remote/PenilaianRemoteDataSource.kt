package com.inertia.data.datasource.remote

import android.util.Log
import com.inertia.data.datasource.remote.api.PenilaianService
import com.inertia.data.datasource.remote.request.StoreFormPenilaianRequest
import com.inertia.data.datasource.remote.response.StoreFormPenilaianResponse
import com.inertia.data.repository.penilaian.IPenilaianRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenilaianRemoteDataSource(private val service: PenilaianService) {

    companion object {
        @Volatile
        private var instance: PenilaianRemoteDataSource? = null

        fun getInstance(service: PenilaianService): PenilaianRemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: PenilaianRemoteDataSource(service).apply {
                    instance = this
                }
            }
    }

    fun store(request: StoreFormPenilaianRequest, callback: IPenilaianRepository.storeFormPenilaianCallback) {
        service.storeFormPenilaian(
            request.nomor_wa,
            request.nmBencana,
            request.idSub,
            request.name,
            request.alamat,
            request.provinsi,
            request.kota,
            request.tanggal,
            request.penilaian
        ).enqueue(object : Callback<StoreFormPenilaianResponse> {
            override fun onResponse(
                call: Call<StoreFormPenilaianResponse>,
                response: Response<StoreFormPenilaianResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        val status = data.status
                        callback.onStoreFormPenilaianSuccessCallback(status)
                    }
                }else{
                    Log.e("Store Penilaian", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoreFormPenilaianResponse>, t: Throwable) {
                Log.e("Store Penilaian", "Error: ${t.message}")
            }

        })
    }
}
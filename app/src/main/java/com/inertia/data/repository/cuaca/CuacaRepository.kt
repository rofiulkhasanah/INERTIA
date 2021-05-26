package com.inertia.data.repository.cuaca

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.local.entity.CuacaEntity
import com.inertia.data.datasource.remote.CuacaRemoteDataSource
import com.inertia.data.datasource.remote.response.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuacaRepository(private val cuacaRemoteDataSource: CuacaRemoteDataSource): ICuacaRepository {

    companion object {
        @Volatile
        private var instance: CuacaRepository? = null

        fun getInstance(remote: CuacaRemoteDataSource): CuacaRepository =
            instance ?: synchronized(this) {
                instance ?: CuacaRepository(remote).apply {
                    instance = this
                }
            }
    }

    override fun getCuaca(latitude: String, longitude: String): LiveData<CuacaEntity> {
    val dataCuaca = MutableLiveData<CuacaEntity>()
    cuacaRemoteDataSource.getCuaca(latitude, longitude).enqueue(object: Callback<WeatherResponse>{
        override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
            if(response.isSuccessful){
                val data = response.body()
                if(data != null){
                    val cuaca = CuacaEntity(
                        data.temp, data.cloud, data.wind, data.humidity)
                    dataCuaca.postValue(cuaca)
                }else{
                    Log.e("Cuaca", "Error: ${response.message()}")
                }
            }
        }
        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            Log.e("Cuaca", "Error: ${t.message}")
        }
    })
        return dataCuaca
    }

}
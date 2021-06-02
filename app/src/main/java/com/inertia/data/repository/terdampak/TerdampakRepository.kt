package com.inertia.data.repository.terdampak

import androidx.lifecycle.LiveData
import com.inertia.data.NetworkBoundResource
import com.inertia.data.datasource.local.TerdampakLocalDataSource
import com.inertia.data.datasource.local.entity.TerdampakEntity
import com.inertia.data.datasource.remote.TerdampakRemoteDataSource
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.TerdampakResponseItem
import com.inertia.utils.AppExecutor
import com.mirfanrafif.kicksfilm.vo.Resource

class TerdampakRepository(private val remote: TerdampakRemoteDataSource, private val local: TerdampakLocalDataSource, private val appExecutor: AppExecutor): ITerdampakRepository {

    companion object {
        @Volatile
        private var instance: TerdampakRepository? = null

        fun getInstance(remote: TerdampakRemoteDataSource, local: TerdampakLocalDataSource, appExecutor: AppExecutor): TerdampakRepository =
            instance ?: synchronized(this) {
                instance ?: TerdampakRepository(remote, local, appExecutor).apply {
                    instance = this
                }
            }
    }

    override fun getTerdampak(nomorWa: String): LiveData<Resource<List<TerdampakEntity>>> {
        return object : NetworkBoundResource<List<TerdampakEntity>, List<TerdampakResponseItem>>(appExecutor){
            override fun loadFromDB(): LiveData<List<TerdampakEntity>> {
                return local.getAllTerdampak()
            }

            override fun shouldFetch(data: List<TerdampakEntity>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<List<TerdampakResponseItem>>> {
                return remote.getAllTerdampak(nomorWa)
            }

            override fun saveCallResult(data: List<TerdampakResponseItem>) {
                val listTerdampak = data.map { item ->
                    TerdampakEntity(
                        idKasus = item.idKasus,
                        nomorWa = item.nomorWa,
                        idBencana = item.idBencana,
                        idSub = item.idSub,
                        nama = item.nama,
                        alamat = item.alamat,
                        provinsi = item.provinsi,
                        kota = item.kota,
                        tanggal = item.tanggal,
                        namaBencana = item.namaBencana
                    )
                }
                local.insertTerdampak(listTerdampak)
            }

        }.asLiveData()
    }
}
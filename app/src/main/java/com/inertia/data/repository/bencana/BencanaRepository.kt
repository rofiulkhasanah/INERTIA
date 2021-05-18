package com.inertia.data.repository.bencana

import android.util.Log
import androidx.lifecycle.LiveData
import com.inertia.data.NetworkBoundResource
import com.inertia.data.datasource.local.BencanaLocalDataSource
import com.inertia.data.datasource.remote.BencanaRemoteDataSource
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.response.BencanaResponse
import com.inertia.utils.AppExecutor
import com.inertia.data.response.ApiResponse
import com.mirfanrafif.kicksfilm.vo.Resource

class BencanaRepository private constructor(
    private val remote: BencanaRemoteDataSource,
    private val local: BencanaLocalDataSource,
    private val appExecutor: AppExecutor
): IBencanaRepository
{
    companion object {
        @Volatile
        private var instance: BencanaRepository? = null

        fun getInstance(remote: BencanaRemoteDataSource, local: BencanaLocalDataSource,
                        appExecutor: AppExecutor): BencanaRepository =
            instance ?: synchronized(this) {
                instance ?: BencanaRepository(remote, local, appExecutor).apply {
                    instance = this
                }
            }
    }

    override fun getAllBencana(): LiveData<Resource<List<BencanaEntity>>> {

        return object : NetworkBoundResource<List<BencanaEntity>, List<BencanaResponse>>(appExecutor) {
            override fun loadFromDB(): LiveData<List<BencanaEntity>> {
                return local.getAllBencana()
            }

            override fun shouldFetch(data: List<BencanaEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<BencanaResponse>>> {
                return remote.getAllBencana()
            }

            override fun saveCallResult(data: List<BencanaResponse>) {
                val data = data.map { item ->
                    BencanaEntity(
                        id = item.id.toInt(),
                        namaBencana = item.namaBencana,
                        jenisBencana = item.jenisBencana,
                        kronologiBencana = item.kronologiBencana,
                        longitude = item.longitude.toDouble(),
                        latitude = item.latitude.toDouble(),
                        createdDate = item.createdAt,
                        linkFoto = "https://placeimg.com/640/480/nature"
                    )
                }
                local.insertBencana(data)

            }

        }.asLiveData()
    }

    override fun getDetailBencana(): LiveData<BencanaEntity> {
        TODO("Not yet implemented")
    }

    override fun saveBencana() {
        TODO("Not yet implemented")
    }
}
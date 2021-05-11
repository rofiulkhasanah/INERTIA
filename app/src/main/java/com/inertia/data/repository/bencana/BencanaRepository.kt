package com.inertia.data.repository.bencana

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.remote.BencanaRemoteDataSource
import com.inertia.data.entity.BencanaEntity
import com.inertia.data.response.BencanaResponse

class BencanaRepository private constructor(private val bencanaRemoteDataSource: BencanaRemoteDataSource): IBencanaRepository
{
    companion object {
        @Volatile
        private var instance: BencanaRepository? = null

        fun getInstance(remoteDataSource: BencanaRemoteDataSource) : BencanaRepository =
            instance ?: synchronized(this) {
                instance ?: BencanaRepository(remoteDataSource).apply {
                    instance = this
                }
            }
    }

    override fun getAllBencana(): LiveData<List<BencanaEntity>> {
        val listBencana = MutableLiveData<List<BencanaEntity>>()

        bencanaRemoteDataSource.getAllBencana(object :
            BencanaRemoteDataSource.LoadAllBencanaCallback {
            override fun onLoadAllBencanaCallback(response: List<BencanaResponse>) {
                val bencanas = ArrayList<BencanaEntity>()
                for (item in response) {
                    val bencana = BencanaEntity(
                        id = item.id.toInt(),
                        namaBencana = item.namaBencana,
                        jenisBencana = item.jenisBencana,
                        kronologiBencana = item.kronologiBencana,
                        longitude = item.longitude.toDouble(),
                        latitude = item.latitude.toDouble(),
                        createdDate = item.createdAt,
                        linkFoto = "https://placeimg.com/640/480/nature"
                    )
                    bencanas.add(bencana)
                }
                listBencana.postValue(bencanas)
            }

        })
        return listBencana
    }

    override fun getDetailBencana(): LiveData<BencanaEntity> {
        TODO("Not yet implemented")
    }

    override fun saveBencana() {
        TODO("Not yet implemented")
    }
}
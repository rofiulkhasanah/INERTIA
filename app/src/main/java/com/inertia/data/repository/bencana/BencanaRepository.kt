package com.inertia.data.repository.bencana

import android.util.Log
import androidx.lifecycle.LiveData
import com.inertia.data.NetworkBoundResource
import com.inertia.data.datasource.local.BencanaLocalDataSource
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.remote.BencanaRemoteDataSource
import com.inertia.data.datasource.remote.request.BencanaRequest
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.BencanaItem
import com.inertia.data.datasource.remote.response.BencanaResponse
import com.inertia.utils.AppExecutor
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

        return object : NetworkBoundResource<List<BencanaEntity>, List<BencanaItem>>(appExecutor) {
            override fun loadFromDB(): LiveData<List<BencanaEntity>> {
                return local.getAllBencana()
            }

            override fun shouldFetch(data: List<BencanaEntity>?): Boolean {
//                if(data != null && data.isNotEmpty()) {
//                    val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.getDefault())
//                    val firstData = data.first()
//                    val now = Date()
//                    val waktuAduan = formatter.parse(firstData.waktuAduan)
//                    return waktuAduan.compareTo(now) < 0
//                }else return true
//                return data == null || data.isEmpty()
                return true
            }

            override fun createCall(): LiveData<ApiResponse<List<BencanaItem>>> {
                return remote.getAllBencana()
            }

            override fun saveCallResult(data: List<BencanaItem>) {
                val listBencana = data.map { item ->
                    val latLongSplit = item.latLong.split(", ")
                    val lat = latLongSplit[0].toDouble()
                    val long = latLongSplit[1].toDouble()
                    Log.d("latlong", "$latLongSplit")
                    BencanaEntity(
                        id = item.id,
                        namaBencana = item.judul,
                        jenisBencana = item.jenisBencana,
                        kronologiBencana = item.kronologi,
                        longitude = lat,
                        latitude = long,
                        waktuBencana = item.waktuBencana,
                        waktuAduan = item.waktuAduan,
                        linkFoto = item.gambarUri
                    )
                }
                local.insertBencana(listBencana)
            }

        }.asLiveData()
    }

    override fun createLaporan(request: BencanaRequest): LiveData<BencanaResponse> = remote.createLaporan(request)
}
package com.inertia.data.repository.bencana

import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Update
import com.inertia.data.NetworkBoundResource
import com.inertia.data.datasource.local.BencanaLocalDataSource
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.remote.BencanaRemoteDataSource
import com.inertia.data.datasource.remote.request.BencanaRequest
import com.inertia.data.datasource.remote.response.*
import com.inertia.utils.AppExecutor
import com.mirfanrafif.kicksfilm.data.source.remote.StatusResponse
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
                    val latLongSplit = item.latLong?.split(",")
                    val lat = latLongSplit?.get(0)?.toDouble()
                    val long = latLongSplit?.get(1)?.toDouble()
                    BencanaEntity(
                        id = item.idAduan,
                        namaBencana = item.judul,
                        jenisBencana = item.jenisBencana,
                        kronologiBencana = item.kronologi,
                        longitude = lat,
                        latitude = long,
                        waktuBencana = item.waktuBencana,
                        waktuAduan = item.waktuBencana,
                        linkFoto = item.gambarUri,
                        nomorWaPengadu = item.senderWaNumber,
                        kota = item.alamat?.city ?: item.alamat?.county,
                        provinsi = item.alamat?.state,
                        uriDonasi = item.url
                    )
                }
                local.insertBencana(listBencana)
            }

        }.asLiveData()
    }

    override fun createLaporan(request: BencanaRequest): LiveData<ApiResponse<LaporResponse>> =
        remote.createLaporan(request)

    override fun getLaporanByNomorWa(nomorWa: String): LiveData<Resource<List<BencanaEntity>>> {
        return object : NetworkBoundResource<List<BencanaEntity>, List<BencanaItem>>(appExecutor) {
            override fun loadFromDB(): LiveData<List<BencanaEntity>> {
                return local.getBencanaByNomorWa(nomorWa)
            }

            override fun shouldFetch(data: List<BencanaEntity>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<List<BencanaItem>>> {
                return remote.getBencanaByNomorWa(nomorWa)
            }

            override fun saveCallResult(data: List<BencanaItem>) {
                val listBencana = data.map { item ->
                    val latLongSplit = item.latLong?.split(",")
                    val lat = latLongSplit?.get(0)?.toDouble()
                    val long = latLongSplit?.get(1)?.toDouble()
                    BencanaEntity(
                        id = item.idAduan,
                        namaBencana = item.judul,
                        jenisBencana = item.jenisBencana,
                        kronologiBencana = item.kronologi,
                        longitude = lat,
                        latitude = long,
                        waktuBencana = item.waktuBencana,
                        waktuAduan = item.waktuBencana,
                        linkFoto = item.gambarUri,
                        nomorWaPengadu = item.senderWaNumber,
                        kota = item.alamat?.city,
                        provinsi = item.alamat?.state,
                        uriDonasi = item.url
                    )
                }
                local.insertBencana(listBencana)
            }

        }.asLiveData()
    }

    override fun updateBencana(idAduan: String, uriDonasi: String): LiveData<BencanaEntity> {
        val apiResponse = remote.updateUriDonasi(idAduan, uriDonasi)

        if (apiResponse != null) {
            when(apiResponse.status) {
                StatusResponse.SUCCESS -> {
                    appExecutor.diskIO().execute { local.updateBencanaUri(idAduan, uriDonasi) }
                }
                else -> {}
            }
        }
        return local.getBencanaById(idAduan)
    }
}
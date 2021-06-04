package com.inertia.data.repository.bencana

import androidx.lifecycle.LiveData
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.remote.request.BencanaRequest
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.BencanaResponse
import com.inertia.data.datasource.remote.response.LaporResponse
import com.mirfanrafif.kicksfilm.vo.Resource

interface IBencanaRepository {
    fun getAllBencana(): LiveData<Resource<List<BencanaEntity>>>

    fun createLaporan(request: BencanaRequest): LiveData<ApiResponse<LaporResponse>>

    fun getLaporanByNomorWa(nomorWa: String): LiveData<Resource<List<BencanaEntity>>>

    fun updateBencana(idAduan: String, uriDonasi: String): LiveData<BencanaEntity>
}
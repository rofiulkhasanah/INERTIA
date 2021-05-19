package com.inertia.data.repository.bencana

import androidx.lifecycle.LiveData
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.mirfanrafif.kicksfilm.vo.Resource

interface IBencanaRepository {
    fun getAllBencana(): LiveData<Resource<List<BencanaEntity>>>

    fun getDetailBencana(): LiveData<BencanaEntity>

    fun saveBencana()
}
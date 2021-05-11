package com.inertia.data.repository.bencana

import androidx.lifecycle.LiveData
import com.inertia.data.entity.BencanaEntity

interface IBencanaRepository {
    fun getAllBencana(): LiveData<List<BencanaEntity>>

    fun getDetailBencana(): LiveData<BencanaEntity>

    fun saveBencana()
}
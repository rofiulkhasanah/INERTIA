package com.inertia.data.repository.terdampak

import androidx.lifecycle.LiveData
import com.inertia.data.datasource.local.entity.TerdampakEntity
import com.mirfanrafif.kicksfilm.vo.Resource

interface ITerdampakRepository {

    fun getTerdampak(nomorWa: String): LiveData<Resource<List<TerdampakEntity>>>
}
package com.inertia.data.repository.cuaca

import androidx.lifecycle.LiveData
import com.inertia.data.datasource.local.entity.CuacaEntity

interface ICuacaRepository {
    fun getCuaca(latitude: String, longitude: String): LiveData<CuacaEntity>
}
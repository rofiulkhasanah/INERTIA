package com.inertia.data.repository.cuaca

import androidx.lifecycle.LiveData
import com.inertia.data.datasource.local.entity.CuacaEntity

interface ICuacaRepository {
    fun getCuaca(latitude: Double, longitude: Double): LiveData<CuacaEntity>
}
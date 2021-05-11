package com.inertia.utils

import com.inertia.data.api.InertiaService
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.datasource.remote.BencanaRemoteDataSource

object Injection {
    fun provideBencanaRepository(): BencanaRepository {
        val inertiaService = InertiaService()
        val bencanaRemoteDataSource = BencanaRemoteDataSource.getInstance(inertiaService)
        return BencanaRepository.getInstance(bencanaRemoteDataSource)
    }
}
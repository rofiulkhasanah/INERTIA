package com.inertia.utils

import android.content.Context
import com.inertia.data.api.InertiaService
import com.inertia.data.datasource.local.BencanaLocalDataSource
import com.inertia.data.datasource.local.database.InertiaDatabase
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.datasource.remote.BencanaRemoteDataSource

object Injection {
    fun provideBencanaRepository(context: Context): BencanaRepository {
        val database = InertiaDatabase.getInstance(context)
        val localDataSource = BencanaLocalDataSource.getInstance(database.bencanaDao())

        val inertiaService = InertiaService()
        val bencanaRemoteDataSource = BencanaRemoteDataSource.getInstance(inertiaService)

        val appExecutor = AppExecutor()

        return BencanaRepository.getInstance(bencanaRemoteDataSource, localDataSource, appExecutor)
    }
}
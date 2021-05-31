package com.inertia.utils

import android.content.Context
import com.inertia.data.datasource.local.BencanaLocalDataSource
import com.inertia.data.datasource.local.TerdampakLocalDataSource
import com.inertia.data.datasource.local.UserLocalDataSource
import com.inertia.data.datasource.local.database.InertiaDatabase
import com.inertia.data.datasource.local.preference.UserPreferences
import com.inertia.data.datasource.remote.BencanaRemoteDataSource
import com.inertia.data.datasource.remote.CuacaRemoteDataSource
import com.inertia.data.datasource.remote.TerdampakRemoteDataSource
import com.inertia.data.datasource.remote.UserRemoteDataSource
import com.inertia.data.datasource.remote.api.InertiaService
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.repository.cuaca.CuacaRepository
import com.inertia.data.repository.terdampak.TerdampakRepository
import com.inertia.data.repository.user.UserRepository

object Injection {
    fun provideBencanaRepository(context: Context): BencanaRepository {
        val database = InertiaDatabase.getInstance(context)
        val localDataSource = BencanaLocalDataSource.getInstance(database.bencanaDao())

        val inertiaService = InertiaService()
        val remote = BencanaRemoteDataSource.getInstance(inertiaService.getBencanaService())

        val appExecutor = AppExecutor()

        return BencanaRepository.getInstance(remote, localDataSource, appExecutor)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val preferences = UserPreferences(context)

        val inertiaService = InertiaService()

        val remote = UserRemoteDataSource(inertiaService.getUserService())
        val local = UserLocalDataSource(preferences)
        return UserRepository(local, remote)
    }

    fun provideCuacaRepository(): CuacaRepository{
        val inertiaService = InertiaService()
        val remote = CuacaRemoteDataSource(inertiaService.getCuacaService())
        return CuacaRepository(remote)
    }

    fun provideTerdampakRepository(context: Context): TerdampakRepository {
        val database = InertiaDatabase.getInstance(context)
        val localDataSource = TerdampakLocalDataSource.getInstance(database.terdampakDao())

        val inertiaService = InertiaService()
        val remote = TerdampakRemoteDataSource.getInstance(inertiaService.getTerdampak())

        val appExecutor = AppExecutor()

        return TerdampakRepository.getInstance(remote, localDataSource, appExecutor)
    }
}
package com.inertia.ui.main

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.repository.cuaca.CuacaRepository
import com.inertia.data.repository.user.UserRepository
import com.inertia.utils.LocationProvider

class MainViewModel(private val bencanaRepository: BencanaRepository,
                    private val userRepository: UserRepository,
                    private val cuacaRepository: CuacaRepository): ViewModel() {
    fun getUser() = userRepository.getUser()

    fun logout() {
        val userEntity = UserEntity()
        userRepository.setUser(userEntity)
    }

    fun getLocation(activity: Activity) = LocationProvider.getLocation(activity)

    fun getAllBencana() = bencanaRepository.getAllBencana()

    fun getCuaca(latitude: Double, longitude: Double) = cuacaRepository.getCuaca(latitude, longitude)
}
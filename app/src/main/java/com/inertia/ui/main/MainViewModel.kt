package com.inertia.ui.main

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.repository.cuaca.CuacaRepository
import com.inertia.data.repository.user.UserRepository
import com.inertia.utils.LocationProvider

class MainViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getUser() = userRepository.getUser()
}
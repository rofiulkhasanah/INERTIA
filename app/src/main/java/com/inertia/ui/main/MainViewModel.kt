package com.inertia.ui.main

import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.repository.user.UserRepository

class MainViewModel(private val bencanaRepository: BencanaRepository,
                    private val userRepository: UserRepository): ViewModel() {
    fun getUser() = userRepository.getUser()

    fun logout() {
        val userEntity = UserEntity()
        userRepository.setUser(userEntity)
    }

    fun getAllBencana() = bencanaRepository.getAllBencana()
}
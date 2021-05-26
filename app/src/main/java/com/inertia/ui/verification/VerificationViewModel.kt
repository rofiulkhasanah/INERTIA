package com.inertia.ui.verification

import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.repository.user.UserRepository

class VerificationViewModel(private val userRepository: UserRepository): ViewModel() {
    fun saveUser(userEntity: UserEntity?) {
        userRepository.setUser(userEntity)
    }
}
package com.inertia.ui.login

import androidx.lifecycle.ViewModel
import com.inertia.data.repository.user.UserRepository

class LoginViewModel(val userRepository: UserRepository): ViewModel() {
    fun login(phoneNumber: String) = userRepository.login(phoneNumber)
}
package com.inertia.ui.login

import androidx.lifecycle.ViewModel
import com.inertia.data.repository.user.IUserRepository
import com.inertia.data.repository.user.UserRepository

class LoginViewModel(val userRepository: UserRepository) : ViewModel() {
    fun login(phoneNumber: String, callback: IUserRepository.LoginCallback) {
        userRepository.login(phoneNumber, callback)
    }
}
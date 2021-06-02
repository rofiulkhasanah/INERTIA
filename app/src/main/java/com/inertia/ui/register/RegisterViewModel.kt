package com.inertia.ui.register

import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.remote.request.RegisterRequest
import com.inertia.data.repository.user.IUserRepository
import com.inertia.data.repository.user.UserRepository

class RegisterViewModel(val userRepository: UserRepository) : ViewModel() {
    fun register(request: RegisterRequest, callback: IUserRepository.RegisterCallback) =
        userRepository.register(request, callback)
}
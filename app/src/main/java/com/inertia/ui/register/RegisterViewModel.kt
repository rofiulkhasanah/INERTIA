package com.inertia.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.remote.request.RegisterRequest
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.RegisterResponse
import com.inertia.data.repository.user.UserRepository

class RegisterViewModel(val userRepository: UserRepository): ViewModel() {
    fun register(request: RegisterRequest): LiveData<ApiResponse<RegisterResponse>> =
        userRepository.register(request)
}
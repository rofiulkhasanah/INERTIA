package com.inertia.ui.main

import androidx.lifecycle.ViewModel
import com.inertia.data.repository.user.UserRepository

class MainViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getUser() = userRepository.getUser()
}
package com.inertia.ui.login

import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.local.entity.UserEntity

class LoginViewModel(val userRepository: String): ViewModel() {
    fun login(phoneNumber: String, callback: LoginCallback): UserEntity {
        val user = UserEntity(
            "Budi Jayanto", "6282338819564",
            "Jawa Timur", "Malang"
        )
        callback.onLoginSuccessCallback(user, "784594")
        return user
    }

    interface LoginCallback {
        fun onLoginSuccessCallback(userEntity: UserEntity, verificationCode: String)
    }
}
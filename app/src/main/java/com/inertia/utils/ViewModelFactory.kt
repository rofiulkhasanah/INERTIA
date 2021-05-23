package com.inertia.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.repository.user.UserRepository
import com.inertia.ui.home.HomeViewModel
import com.inertia.ui.login.LoginViewModel
import com.inertia.ui.profile.ProfileViewModel
import com.inertia.ui.verification.VerificationViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor (
    private val bencanaRepository: BencanaRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideBencanaRepository(context),
                Injection.provideUserRepository(context)
            )
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(bencanaRepository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(userRepository) as T
            modelClass.isAssignableFrom(VerificationViewModel::class.java) -> VerificationViewModel(userRepository) as T
            else -> throw Throwable("Unknown viewmodel class")
        }
    }
}
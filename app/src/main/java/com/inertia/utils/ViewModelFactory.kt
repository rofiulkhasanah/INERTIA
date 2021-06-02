package com.inertia.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.repository.cuaca.CuacaRepository
import com.inertia.data.repository.terdampak.TerdampakRepository
import com.inertia.data.repository.user.UserRepository
import com.inertia.ui.form.FormViewModel
import com.inertia.ui.login.LoginViewModel
import com.inertia.ui.main.MainViewModel
import com.inertia.ui.register.RegisterViewModel
import com.inertia.ui.terdampak.TerdampakViewModel
import com.inertia.ui.verification.VerificationViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor (
    private val bencanaRepository: BencanaRepository,
    private val userRepository: UserRepository,
    private val cuacaRepository: CuacaRepository,
    private val terdampakRepository: TerdampakRepository
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideBencanaRepository(context),
                Injection.provideUserRepository(context),
                Injection.provideCuacaRepository(),
                Injection.provideTerdampakRepository(context)

            )
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(bencanaRepository, userRepository, cuacaRepository) as T
            modelClass.isAssignableFrom(VerificationViewModel::class.java) -> VerificationViewModel(userRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(userRepository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(TerdampakViewModel::class.java) -> TerdampakViewModel(terdampakRepository) as T
            modelClass.isAssignableFrom(FormViewModel::class.java) -> FormViewModel(bencanaRepository, userRepository) as T
            else -> throw Throwable("Unknown viewmodel class")
        }
    }
}
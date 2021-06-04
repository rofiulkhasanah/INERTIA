package com.inertia.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.repository.cuaca.CuacaRepository
import com.inertia.data.repository.user.UserRepository
import com.inertia.ui.detail.DetailReportViewModel
import com.inertia.ui.form.FormViewModel
import com.inertia.ui.home.HomeViewModel
import com.inertia.ui.laporanmu.LaporanmuViewModel
import com.inertia.ui.login.LoginViewModel
import com.inertia.ui.main.MainViewModel
import com.inertia.ui.profile.ProfileViewModel
import com.inertia.ui.register.RegisterViewModel
import com.inertia.ui.verification.VerificationViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor (
    private val bencanaRepository: BencanaRepository,
    private val userRepository: UserRepository,
    private val cuacaRepository: CuacaRepository
    ) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideBencanaRepository(context),
                Injection.provideUserRepository(context),
                Injection.provideCuacaRepository(),
            )
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(userRepository) as T
            modelClass.isAssignableFrom(VerificationViewModel::class.java) -> VerificationViewModel(userRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(userRepository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(FormViewModel::class.java) -> FormViewModel(bencanaRepository, userRepository) as T
            modelClass.isAssignableFrom(LaporanmuViewModel::class.java) -> LaporanmuViewModel(bencanaRepository, userRepository) as T
            modelClass.isAssignableFrom(DetailReportViewModel::class.java) -> DetailReportViewModel(bencanaRepository, userRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(bencanaRepository, userRepository, cuacaRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(userRepository) as T
            else -> throw Throwable("Unknown viewmodel class")
        }
    }
}
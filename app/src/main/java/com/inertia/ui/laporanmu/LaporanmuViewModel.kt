package com.inertia.ui.laporanmu

import androidx.lifecycle.ViewModel
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.repository.user.UserRepository

class LaporanmuViewModel(private val bencanaRepository: BencanaRepository, private val userRepository: UserRepository): ViewModel() {
    fun getUser() = userRepository.getUser()

    fun getBencanaByNomorWa(nomorWa: String) = bencanaRepository.getLaporanByNomorWa(nomorWa)
}
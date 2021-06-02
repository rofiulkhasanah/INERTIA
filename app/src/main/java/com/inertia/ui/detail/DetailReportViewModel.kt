package com.inertia.ui.detail

import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.repository.user.UserRepository

class DetailReportViewModel(val bencanaRepository: BencanaRepository, val userRepository: UserRepository): ViewModel() {
    fun getUser() = userRepository.getUser()

    fun updateBencana(bencanaEntity: BencanaEntity) = bencanaRepository.updateBencana(bencanaEntity)
}
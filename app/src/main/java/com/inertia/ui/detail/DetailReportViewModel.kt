package com.inertia.ui.detail

import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.data.repository.user.UserRepository

class DetailReportViewModel(private val bencanaRepository: BencanaRepository, private val userRepository: UserRepository): ViewModel() {
    fun getUser() = userRepository.getUser()

    fun updateBencana(idAduan: String, url: String) = bencanaRepository.updateBencana(idAduan, url)
}
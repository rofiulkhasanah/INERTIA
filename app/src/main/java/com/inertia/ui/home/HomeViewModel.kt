package com.inertia.ui.home

import androidx.lifecycle.ViewModel
import com.inertia.data.repository.bencana.BencanaRepository

class HomeViewModel(private val bencanaRepository: BencanaRepository): ViewModel() {
    fun getAllBencana() = bencanaRepository.getAllBencana()
}
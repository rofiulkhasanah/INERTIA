package com.inertia.ui.home

import androidx.lifecycle.ViewModel
import com.inertia.data.repository.bencana.BencanaRepository

class HomeViewModel(private val repository: BencanaRepository): ViewModel() {
    fun getAllBencana() = repository.getAllBencana()
}
package com.inertia.ui.terdampak

import androidx.lifecycle.ViewModel
import com.inertia.data.repository.penilaian.PenilaianRepository
import com.inertia.data.repository.terdampak.TerdampakRepository

class TerdampakViewModel(private val terdampakRepository: TerdampakRepository): ViewModel(){
    fun getAllTerdampak(nomorWa: String) = terdampakRepository.getTerdampak(nomorWa)
}
package com.inertia.ui.assessment

import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.remote.request.StoreFormPenilaianRequest
import com.inertia.data.repository.penilaian.IPenilaianRepository
import com.inertia.data.repository.penilaian.PenilaianRepository

class AssessmentViewModel(private val penilaianRepository: PenilaianRepository): ViewModel() {
    fun store(request: StoreFormPenilaianRequest,
              callback: IPenilaianRepository.storeFormPenilaianCallback)
            = penilaianRepository.storeFormPenilaian(request, callback)
}
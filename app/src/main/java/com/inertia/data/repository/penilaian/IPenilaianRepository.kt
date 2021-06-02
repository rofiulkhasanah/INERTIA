package com.inertia.data.repository.penilaian

import com.inertia.data.datasource.remote.request.StoreFormPenilaianRequest

interface IPenilaianRepository {
    fun storeFormPenilaian(request: StoreFormPenilaianRequest, callback: storeFormPenilaianCallback)

    interface storeFormPenilaianCallback {
        fun onStoreFormPenilaianSuccessCallback(status: Boolean?)
    }
}
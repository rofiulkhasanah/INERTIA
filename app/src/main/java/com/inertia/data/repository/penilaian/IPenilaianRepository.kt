package com.inertia.data.repository.penilaian

import androidx.lifecycle.LiveData
import com.inertia.data.datasource.local.entity.TerdampakEntity
import com.inertia.data.datasource.remote.request.StoreFormPenilaianRequest
import com.mirfanrafif.kicksfilm.vo.Resource

interface IPenilaianRepository {
    fun storeFormPenilaian(request: StoreFormPenilaianRequest, callback: storeFormPenilaianCallback)

    interface storeFormPenilaianCallback {
        fun onStoreFormPenilaianSuccessCallback(status: Boolean?)
    }
}
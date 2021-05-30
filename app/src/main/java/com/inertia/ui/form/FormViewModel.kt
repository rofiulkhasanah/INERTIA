package com.inertia.ui.form

import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.remote.request.BencanaRequest
import com.inertia.data.repository.bencana.IBencanaRepository
import com.inertia.data.repository.user.UserRepository

class FormViewModel(private val bencana: IBencanaRepository, private val user: UserRepository): ViewModel() {
    fun createLaporan(request: BencanaRequest) = bencana.createLaporan(request)

    fun getUser() = user.getUser()
}
package com.inertia.ui.form

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.remote.request.BencanaRequest
import com.inertia.data.repository.bencana.IBencanaRepository
import com.inertia.data.repository.user.UserRepository
import com.inertia.utils.LocationProvider

class FormViewModel(private val bencana: IBencanaRepository,
                    private val user: UserRepository): ViewModel() {
    fun createLaporan(request: BencanaRequest) = bencana.createLaporan(request)

    fun getUser() = user.getUser()

    fun getLocation(activity: Activity) = LocationProvider.getLocation(activity)
}
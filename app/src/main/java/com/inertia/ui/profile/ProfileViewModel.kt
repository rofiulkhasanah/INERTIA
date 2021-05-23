package com.inertia.ui.profile

import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.preference.UserPreferences

class ProfileViewModel(val preferences: UserPreferences): ViewModel() {
    fun getUser() = preferences.getUser()

    fun setUser(userEntity: UserEntity) = preferences.setUser(userEntity)
}
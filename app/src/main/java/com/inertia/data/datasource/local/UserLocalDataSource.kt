package com.inertia.data.datasource.local

import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.local.preference.UserPreferences

class UserLocalDataSource(private val preferences: UserPreferences) {
    fun getUser() = preferences.getUser()

    fun setUser(userEntity: UserEntity?) = preferences.setUser(userEntity)
}
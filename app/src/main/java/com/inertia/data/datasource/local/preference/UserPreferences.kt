package com.inertia.data.datasource.local.preference

import android.content.Context
import androidx.core.content.edit
import com.inertia.data.datasource.local.entity.UserEntity

class UserPreferences(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val PHONE_NUMBER = "phone_number"
        private const val KOTA = "kota"
        private const val PROVINSI = "provinsi"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(user: UserEntity?) {
        preferences.edit {
            putString(NAME, user?.name)
            putString(PHONE_NUMBER, user?.phoneNumber)
            putString(KOTA, user?.kota)
            putString(PROVINSI, user?.provinsi)
        }
    }

    fun getUser(): UserEntity {
        val user = UserEntity()
        user.name = preferences.getString(NAME, null)
        user.phoneNumber = preferences.getString(PHONE_NUMBER, null)
        user.kota = preferences.getString(KOTA, null)
        user.provinsi = preferences.getString(PROVINSI, null)
        return user
    }
}
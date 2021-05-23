package com.inertia.data.preference

import android.content.Context
import androidx.core.content.edit
import com.inertia.data.datasource.local.entity.UserEntity

class UserPreferences(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val PHONE_NUMBER = "phone_number"
        private const val API_KEY = "api_key"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(user: UserEntity?) {
        preferences.edit {
            putString(NAME, user?.name)
            putString(PHONE_NUMBER, user?.phoneNumber)
            putString(API_KEY, user?.api_key)
        }
    }

    fun getUser(): UserEntity {
        val user = UserEntity()
        user.name = preferences.getString(NAME, null)
        user.phoneNumber = preferences.getString(PHONE_NUMBER, null)
        user.api_key = preferences.getString(API_KEY, null)
        return user
    }
}
package com.inertia.utils

import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.response.LoginResponse
import java.lang.Exception

object DataMapper {
    fun mapLoginResponseToUserEntity(data: LoginResponse): UserEntity {
        return UserEntity(data.nama, data.jenisPengguna, data.nomorWa,
            data.jenisKelamin, data.alamat)
    }

    fun getValidNumber(nomorWa: String): String {
        return try {
            "62${nomorWa.toInt()}"
        }catch (e: Exception) ({
            e.message
        }).toString()
    }
}
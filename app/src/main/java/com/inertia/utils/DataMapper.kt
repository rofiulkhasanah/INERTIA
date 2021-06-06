package com.inertia.utils

import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.response.LoginResponse
import com.inertia.data.datasource.remote.response.RegisterResponse

object DataMapper {
    fun mapLoginResponseToUserEntity(data: LoginResponse): UserEntity {
        return UserEntity(data.nama, data.jenisPengguna, data.nomorWa,
            data.jenisKelamin, data.alamat)
    }

    fun mapRegisterResponseToUserEntity(data: RegisterResponse): UserEntity {
        return UserEntity(data.nama, data.jenisPengguna, data.nomorWa,
            data.jenisKelamin, data.alamat)
    }

    fun getValidNumber(nomorWa: String): String {
        return if(nomorWa.startsWith("0")) {
            "62${nomorWa.removePrefix("0")}"
        }else{
            nomorWa
        }
    }
}
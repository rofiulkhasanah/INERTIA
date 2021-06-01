package com.inertia.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class StoreFormPenilaianResponse(
    @field:SerializedName("status")
    val status: Boolean,

    @field:SerializedName("idKasus")
    val idKasus: String
)

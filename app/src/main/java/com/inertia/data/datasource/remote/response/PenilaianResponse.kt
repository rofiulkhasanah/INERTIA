package com.inertia.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class PenilaianResponse(
    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("provinsi")
    val provinsi: String,

    @field:SerializedName("kota")
    val kota: String,

    @field:SerializedName("nmBencana")
    val nmBencana: String,

//    @field:SerializedName("nmSub")
//    val nmSub: String,
//
//    @field:SerializedName("hasil")
//    val hasil: String,

    @field:SerializedName("nmAlternatif")
    val namaAlternatif: String,
)

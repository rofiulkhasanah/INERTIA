package com.inertia.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class BencanaResponse(

    @field:SerializedName("result")
    val result: List<BencanaItem>,
)

data class BencanaItem(

    @field:SerializedName("id_aduan")
    val id: String,

    @field:SerializedName("waktu_aduan")
    val waktuAduan: String,

    @field:SerializedName("kronologi")
    val kronologi: String,

    @field:SerializedName("lat_long")
    val latLong: String,

    @field:SerializedName("sender_wa_number")
    val senderWaNumber: String,

    @field:SerializedName("gambar_uri")
    val gambarUri: String,

    @field:SerializedName("jenis_bencana")
    val jenisBencana: String,

    @field:SerializedName("waktu_bencana")
    val waktuBencana: String,

    @field:SerializedName("judul")
    val judul: String,
)

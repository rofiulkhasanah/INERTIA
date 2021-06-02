package com.inertia.data.datasource.remote.request

data class StoreFormPenilaianRequest(
    val nomor_wa: String?,
    val nmBencana: String?,
    val idSub: Int?,
    val name: String?,
    val alamat: String?,
    val provinsi: String?,
    val kota: String?,
    val tanggal: String?,
    val penilaian: String?
)

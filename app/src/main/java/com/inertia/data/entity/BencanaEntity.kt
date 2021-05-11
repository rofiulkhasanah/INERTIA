package com.inertia.data.entity

import java.util.*

data class BencanaEntity(
    var id: Int?,
    var namaBencana: String?,
    //jenis bencana di generate aja. tinggal get
    var jenisBencana: String?,
    var kronologiBencana: String?,
    var longitude: Double?,
    var latitude: Double?,
    var createdDate: String?,
    var linkFoto: String?
)
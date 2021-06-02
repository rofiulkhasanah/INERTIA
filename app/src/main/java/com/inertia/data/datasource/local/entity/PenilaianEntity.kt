package com.inertia.data.datasource.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PenilaianEntity(
    var idKriteria: String? = null,
    var idSkala: String? = null,
    var namaSkala: String? = null,
) : Parcelable

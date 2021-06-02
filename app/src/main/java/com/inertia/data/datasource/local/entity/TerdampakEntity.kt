package com.inertia.data.datasource.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class TerdampakEntity(
    var idKasus: String,
    val nomorWa: String,
    val idBencana: String,
    val idSub: String,
    val nama: String,
    val alamat: String,
    val provinsi: String,
    val kota: String,
    val tanggal: String,
    val namaBencana: String
): Parcelable
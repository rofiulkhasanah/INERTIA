package com.inertia.data.datasource.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "terdampakEntity")
data class TerdampakEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idKasus")
    var idKasus: Int,

    @ColumnInfo(name = "nomorWa")
    val nomorWa: String,

    @ColumnInfo(name = "idBencana")
    val idBencana: Int,

    @ColumnInfo(name = "idSub")
    val idSub: Int,

    @ColumnInfo(name = "nama")
    val nama: String,

    @ColumnInfo(name = "alamat")
    val alamat: String,

    @ColumnInfo(name = "provinsi")
    val provinsi: String,

    @ColumnInfo(name = "kota")
    val kota: String,

    @ColumnInfo(name = "tanggal")
    val tanggal: String,

    @ColumnInfo(name = "namaBencana")
    val namaBencana: String
): Parcelable
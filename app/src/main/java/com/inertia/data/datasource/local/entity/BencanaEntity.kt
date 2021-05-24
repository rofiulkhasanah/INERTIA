package com.inertia.data.datasource.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bencanaEntity")
data class BencanaEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idbencana")
    var id: String,

    @ColumnInfo(name = "namaBencana")
    var namaBencana: String?,
    //jenis bencana di generate aja. tinggal get
    @ColumnInfo(name = "jenisBencana")
    var jenisBencana: String?,

    @ColumnInfo(name = "kronologiBencana")
    var kronologiBencana: String?,

    @ColumnInfo(name = "longitude")
    var longitude: Double?,

    @ColumnInfo(name = "latitude")
    var latitude: Double?,

    @ColumnInfo(name = "waktu_aduan")
    var waktuAduan: String?,

    @ColumnInfo(name = "waktu_bencana")
    var waktuBencana: String?,

    @ColumnInfo(name = "linkFoto")
    var linkFoto: String?
)
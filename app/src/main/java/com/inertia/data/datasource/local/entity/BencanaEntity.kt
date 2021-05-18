package com.inertia.data.datasource.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "bencanaEntity")
data class BencanaEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idbencana")
    var id: Int,

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

    @ColumnInfo(name = "createdDate")
    var createdDate: String?,

    @ColumnInfo(name = "linkFoto")
    var linkFoto: String?
)
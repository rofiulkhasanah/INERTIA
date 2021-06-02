package com.inertia.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inertia.data.datasource.local.entity.BencanaEntity

@Dao
interface BencanaDao {
    @Query("SELECT * FROM bencanaEntity")
    fun getAllBencana(): LiveData<List<BencanaEntity>>

    @Query("SELECT * FROM bencanaEntity WHERE sender_wa_number = :nomorWa")
    fun getBencanaByNomorWa(nomorWa: String): LiveData<List<BencanaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBencana(list: List<BencanaEntity>)
}
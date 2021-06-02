package com.inertia.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.inertia.data.datasource.local.entity.BencanaEntity

@Dao
interface BencanaDao {
    @Query("SELECT * FROM bencanaEntity ORDER BY idbencana")
    fun getAllBencana(): LiveData<List<BencanaEntity>>

    @Query("SELECT * FROM bencanaEntity WHERE sender_wa_number = :nomorWa ORDER BY idbencana")
    fun getBencanaByNomorWa(nomorWa: String): LiveData<List<BencanaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBencana(list: List<BencanaEntity>)

//    @Query("""
//        UPDATE bencanaEntity
//        SET uri_donasi = :uriDonasi
//        WHERE idbencana = :idAduan
//    """)
    @Update(entity = BencanaEntity::class)
    fun updateBencana(bencanaEntity: BencanaEntity): Int
}
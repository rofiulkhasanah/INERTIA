package com.inertia.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.local.entity.TerdampakEntity

@Dao
interface TerdampakDao {
    @Query("SELECT * FROM terdampakEntity")
    fun getAllTerdampak(): LiveData<List<TerdampakEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTerdampak(list: List<TerdampakEntity>)
}
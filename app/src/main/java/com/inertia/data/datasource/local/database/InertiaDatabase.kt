package com.inertia.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.inertia.data.datasource.local.dao.BencanaDao
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.local.entity.TerdampakEntity

@Database(
    entities = [BencanaEntity::class], version = 1)
abstract class InertiaDatabase : RoomDatabase(){
    abstract fun bencanaDao(): BencanaDao

    companion object {
        @Volatile
        private var instance: InertiaDatabase? = null

        fun getInstance(context: Context): InertiaDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        InertiaDatabase::class.java, "kicksfilm")
                        .build()
                }
            }
            return instance as InertiaDatabase
        }
    }


}
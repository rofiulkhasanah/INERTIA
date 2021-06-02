package com.inertia.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.inertia.data.datasource.local.dao.BencanaDao
import com.inertia.data.datasource.local.entity.BencanaEntity

@Database(
    entities = [BencanaEntity::class], version = 2)
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
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return instance as InertiaDatabase
        }
        private val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE bencanaEntity ADD COLUMN sender_wa_number TEXT")
            }
        }
    }


}
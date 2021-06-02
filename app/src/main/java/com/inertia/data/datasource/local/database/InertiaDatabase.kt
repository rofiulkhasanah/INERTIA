package com.inertia.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.inertia.data.datasource.local.dao.BencanaDao
import com.inertia.data.datasource.local.dao.TerdampakDao
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.local.entity.TerdampakEntity

@Database(
    entities = [BencanaEntity::class, TerdampakEntity::class], version = 3)
abstract class InertiaDatabase : RoomDatabase(){
    abstract fun bencanaDao(): BencanaDao
    abstract fun terdampakDao(): TerdampakDao

    companion object {
        @Volatile
        private var instance: InertiaDatabase? = null

        fun getInstance(context: Context): InertiaDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        InertiaDatabase::class.java, "kicksfilm")
                        .addMigrations(migration_1_2, migration_2_3)
                        .build()
                }
            }
            return instance as InertiaDatabase
        }

        val migration_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                ALTER TABLE bencanaEntity
                ADD COLUMN sender_wa_number TEXT
            """.trimIndent())
            }

        }

        val migration_2_3 = object : Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                ALTER TABLE bencanaEntity
                ADD COLUMN kota TEXT,
                ADD COLUMN provinsi TEXT
            """.trimIndent())
            }

        }
    }


}
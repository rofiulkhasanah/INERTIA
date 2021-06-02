package com.inertia.data.datasource.local

import com.inertia.data.datasource.local.dao.TerdampakDao
import com.inertia.data.datasource.local.entity.TerdampakEntity

class TerdampakLocalDataSource private constructor(private val terdampakDao: TerdampakDao) {

    companion object {
        @Volatile
        private var instance: TerdampakLocalDataSource? = null

        fun getInstance(terdampakDao: TerdampakDao): TerdampakLocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: TerdampakLocalDataSource(terdampakDao).apply {
                    instance = this
                }
            }
        }
    }

    fun getAllTerdampak() = terdampakDao.getAllTerdampak()

    fun insertTerdampak(list: List<TerdampakEntity>) = terdampakDao.insertTerdampak(list)
}
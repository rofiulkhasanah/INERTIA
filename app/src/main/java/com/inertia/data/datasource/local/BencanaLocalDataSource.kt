package com.inertia.data.datasource.local

import com.inertia.data.datasource.local.dao.BencanaDao
import com.inertia.data.datasource.local.entity.BencanaEntity

class BencanaLocalDataSource private constructor(private val bencanaDao: BencanaDao) {

    companion object {
        @Volatile
        private var instance: BencanaLocalDataSource? = null

        fun getInstance(bencanaDao: BencanaDao): BencanaLocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: BencanaLocalDataSource(bencanaDao).apply {
                    instance = this
                }
            }
        }
    }

    fun getAllBencana() = bencanaDao.getAllBencana()

    fun getBencanaByNomorWa(nomorWa: String) = bencanaDao.getBencanaByNomorWa(nomorWa)

    fun insertBencana(list: List<BencanaEntity>) = bencanaDao.insertBencana(list)
}
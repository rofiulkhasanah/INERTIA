package com.inertia.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inertia.data.repository.bencana.BencanaRepository
import com.inertia.ui.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor (private val bencanaRepository: BencanaRepository):
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideBencanaRepository(context))
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(bencanaRepository) as T
            else -> throw Throwable("Unknown viewmodel class")
        }
    }
}
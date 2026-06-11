package com.example.pharmatic

import android.app.Application
import com.example.pharmatic.data.DataManager
import com.example.pharmatic.data.database.PharmaTicDatabase
import com.example.pharmatic.data.repository.PharmaTicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PharmaTicApp : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PharmaTicDatabase.getDatabase(this) }
    val repository by lazy {
        PharmaTicRepository(
            database.obatDao(),
            database.kasirDao(),
            database.suplierDao(),
            database.transaksiDao(),
            database.transaksiDetailDao()
        )
    }

    override fun onCreate() {
        super.onCreate()
        
        // Seed database
        applicationScope.launch {
            DataManager.seedDatabase(database)
        }
    }
}

package com.example.pharmatic.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pharmatic.Kasir
import com.example.pharmatic.Suplier
import com.example.pharmatic.Transaksi
import com.example.pharmatic.TransaksiDetail
import com.example.pharmatic.data.dao.KasirDao
import com.example.pharmatic.data.dao.ObatDao
import com.example.pharmatic.data.dao.SuplierDao
import com.example.pharmatic.data.dao.TransaksiDao
import com.example.pharmatic.data.dao.TransaksiDetailDao
import com.example.pharmatic.obat.Obat

@Database(
    entities = [Obat::class, Kasir::class, Suplier::class, Transaksi::class, TransaksiDetail::class],
    version = 4,
    exportSchema = false
)
abstract class PharmaTicDatabase : RoomDatabase() {

    abstract fun obatDao(): ObatDao
    abstract fun kasirDao(): KasirDao
    abstract fun suplierDao(): SuplierDao
    abstract fun transaksiDao(): TransaksiDao
    abstract fun transaksiDetailDao(): TransaksiDetailDao

    companion object {
        @Volatile
        private var INSTANCE: PharmaTicDatabase? = null

        fun getDatabase(context: Context): PharmaTicDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PharmaTicDatabase::class.java,
                    "pharmatic_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

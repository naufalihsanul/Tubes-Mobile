package com.example.pharmatic.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pharmatic.TransaksiDetail

@Dao
interface TransaksiDetailDao {
    @Query("SELECT * FROM transaksi_detail WHERE transaksiId = :transaksiId")
    fun getDetailsByTransaksi(transaksiId: String): LiveData<List<TransaksiDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(details: List<TransaksiDetail>)
}

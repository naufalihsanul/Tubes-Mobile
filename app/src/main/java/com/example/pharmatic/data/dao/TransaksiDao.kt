package com.example.pharmatic.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pharmatic.Transaksi

@Dao
interface TransaksiDao {
    @Query("SELECT * FROM transaksi ORDER BY id DESC")
    fun getAllTransaksi(): LiveData<List<Transaksi>>

    @Query("SELECT * FROM transaksi ORDER BY id DESC")
    suspend fun getAllTransaksiList(): List<Transaksi>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaksi: Transaksi): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transaksiList: List<Transaksi>)

    @Update
    suspend fun update(transaksi: Transaksi)

    @Delete
    suspend fun delete(transaksi: Transaksi)

    @Query("DELETE FROM transaksi")
    suspend fun deleteAll()
}

package com.example.pharmatic.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pharmatic.model.Kasir

@Dao
interface KasirDao {
    @Query("SELECT * FROM kasir ORDER BY nama ASC")
    fun getAllKasir(): LiveData<List<Kasir>>

    @Query("SELECT * FROM kasir ORDER BY nama ASC")
    suspend fun getAllKasirList(): List<Kasir>

    @Query("SELECT * FROM kasir WHERE username = :user AND password = :pass LIMIT 1")
    suspend fun verifyLogin(user: String, pass: String): Kasir?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(kasir: Kasir): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(kasirList: List<Kasir>)

    @Update
    suspend fun update(kasir: Kasir)

    @Delete
    suspend fun delete(kasir: Kasir)

    @Query("DELETE FROM kasir")
    suspend fun deleteAll()
}

package com.example.pharmatic.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pharmatic.model.Suplier

@Dao
interface SuplierDao {
    @Query("SELECT * FROM suplier ORDER BY nama ASC")
    fun getAllSuplier(): LiveData<List<Suplier>>

    @Query("SELECT * FROM suplier ORDER BY nama ASC")
    suspend fun getAllSuplierList(): List<Suplier>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(suplier: Suplier): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(suplierList: List<Suplier>)

    @Update
    suspend fun update(suplier: Suplier)

    @Delete
    suspend fun delete(suplier: Suplier)

    @Query("DELETE FROM suplier")
    suspend fun deleteAll()
}

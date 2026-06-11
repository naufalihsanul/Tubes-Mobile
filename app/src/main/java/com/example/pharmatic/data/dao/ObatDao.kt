package com.example.pharmatic.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pharmatic.obat.Obat

@Dao
interface ObatDao {
    @Query("SELECT * FROM obat ORDER BY nama ASC")
    fun getAllObat(): LiveData<List<Obat>>

    @Query("SELECT * FROM obat ORDER BY nama ASC")
    suspend fun getAllObatList(): List<Obat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obat: Obat): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(obatList: List<Obat>)

    @Update
    suspend fun update(obat: Obat)

    @Delete
    suspend fun delete(obat: Obat)

    @Query("SELECT * FROM obat WHERE stok <= 5")
    suspend fun getObatStokRendah(): List<Obat>

    @Query("DELETE FROM obat")
    suspend fun deleteAll()
}

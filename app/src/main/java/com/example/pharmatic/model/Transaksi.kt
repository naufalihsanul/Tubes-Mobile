package com.example.pharmatic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "transaksi",
    indices = [androidx.room.Index(value = ["idTransaksi"], unique = true)]
)
data class Transaksi(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idTransaksi: String,
    val tanggal: String,
    val totalItem: Int,
    val totalHarga: Int,
    val kasirNama: String? = null
) : java.io.Serializable

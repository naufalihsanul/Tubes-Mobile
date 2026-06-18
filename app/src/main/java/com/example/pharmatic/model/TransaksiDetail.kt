package com.example.pharmatic.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transaksi_detail",
    foreignKeys = [
        ForeignKey(
            entity = Transaksi::class,
            parentColumns = ["idTransaksi"],
            childColumns = ["transaksiId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index(value = ["transaksiId"])]
)
data class TransaksiDetail(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val transaksiId: String,
    val namaObat: String,
    val hargaSatuan: Int,
    val qty: Int,
    val subtotal: Int
) : java.io.Serializable

package com.example.pharmatic.obat

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.pharmatic.Suplier

@Entity(
    tableName = "obat",
    foreignKeys = [
        ForeignKey(
            entity = Suplier::class,
            parentColumns = ["id"],
            childColumns = ["suplierId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [androidx.room.Index(value = ["suplierId"])]
)
data class Obat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val harga: Int,
    var stok: Int,
    val jenis: String = "",
    val deskripsi: String = "",
    val imageUri: String? = null,
    val expiredDate: String? = null,
    val barcode: String? = null,
    val suplierId: Int? = null
) : java.io.Serializable

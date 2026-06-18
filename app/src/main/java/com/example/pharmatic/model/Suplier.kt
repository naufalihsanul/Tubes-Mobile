package com.example.pharmatic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suplier")
data class Suplier(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val alamat: String,
    val telepon: String,
    val email: String? = null,
    val logoUri: String? = null
) : java.io.Serializable

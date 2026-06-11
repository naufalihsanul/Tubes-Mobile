package com.example.pharmatic

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kasir")
data class Kasir(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val username: String,
    val password: String,
    val fotoUri: String? = null
) : java.io.Serializable {
    companion object {
        fun hashPassword(password: String): String {
            val bytes = password.toByteArray()
            val md = java.security.MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }
    }
}

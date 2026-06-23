package com.example.pharmatic.kasir

import com.example.pharmatic.R

import com.example.pharmatic.model.Kasir

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pharmatic.viewmodel.KasirViewModel
import java.io.File
import java.util.UUID

class TambahKasirActivity : AppCompatActivity() {
    private lateinit var kasirViewModel: KasirViewModel
    private var selectedFotoUri: Uri? = null

    private val pickFotoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // Salin foto ke penyimpanan internal agar tetap bisa diakses setelah restart
            val savedUri = copyImageToInternalStorage(uri)
            if (savedUri != null) {
                selectedFotoUri = savedUri
                val ivFoto = findViewById<ImageView>(R.id.ivFotoKasir)
                ivFoto.setImageURI(savedUri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_kasir)

        kasirViewModel = ViewModelProvider(this)[KasirViewModel::class.java]

        val etNama = findViewById<EditText>(R.id.etNamaKasirBaru)
        val etUsername = findViewById<EditText>(R.id.etUsernameKasirBaru)
        val etPassword = findViewById<EditText>(R.id.etPasswordKasirBaru)
        val btnPilihFoto = findViewById<Button>(R.id.btnPilihFotoKasir)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanKasir)

        btnPilihFoto.setOnClickListener {
            pickFotoLauncher.launch("image/*")
        }

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val user = etUsername.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (nama.isNotEmpty() && user.isNotEmpty() && pass.isNotEmpty()) {
                kasirViewModel.insert(Kasir(
                    nama = nama, 
                    username = user, 
                    password = Kasir.hashPassword(pass),
                    fotoUri = selectedFotoUri?.toString()
                ))
                Toast.makeText(this, "Akun kasir $nama berhasil dibuat!", Toast.LENGTH_SHORT).show()
                finish() // Menutup halaman dan kembali ke daftar kasir
            } else {
                Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun copyImageToInternalStorage(sourceUri: Uri): Uri? {
        return try {
            val inputStream = contentResolver.openInputStream(sourceUri) ?: return null
            val fileName = "kasir_${UUID.randomUUID()}.jpg"
            val file = File(filesDir, fileName)
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            inputStream.close()
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

package com.example.pharmatic

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

class TambahKasirActivity : AppCompatActivity() {
    private lateinit var kasirViewModel: KasirViewModel
    private var selectedFotoUri: Uri? = null

    private val pickFotoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedFotoUri = uri
            val ivFoto = findViewById<ImageView>(R.id.ivFotoKasir)
            ivFoto.setImageURI(uri)
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
}

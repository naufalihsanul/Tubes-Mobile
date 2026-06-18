package com.example.pharmatic.suplier

import com.example.pharmatic.R

import com.example.pharmatic.model.Suplier

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pharmatic.viewmodel.SuplierViewModel

import android.net.Uri
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class TambahSuplierActivity : AppCompatActivity() {
    private lateinit var suplierViewModel: SuplierViewModel
    private var selectedLogoUri: Uri? = null

    private val pickLogoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedLogoUri = uri
            val ivLogo = findViewById<ImageView>(R.id.ivLogoSuplier)
            ivLogo.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_suplier)

        suplierViewModel = ViewModelProvider(this)[SuplierViewModel::class.java]

        val etNama = findViewById<EditText>(R.id.etNamaSuplier)
        val etAlamat = findViewById<EditText>(R.id.etAlamatSuplier)
        val etTelp = findViewById<EditText>(R.id.etTelpSuplier)
        val etEmail = findViewById<EditText>(R.id.etEmailSuplier)
        val btnPilihLogo = findViewById<Button>(R.id.btnPilihLogoSuplier)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanSuplier)

        btnPilihLogo.setOnClickListener {
            pickLogoLauncher.launch("image/*")
        }

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val telp = etTelp.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (nama.isNotEmpty() && alamat.isNotEmpty() && telp.isNotEmpty()) {
                val newSuplier = Suplier(
                    nama = nama, 
                    alamat = alamat, 
                    telepon = telp,
                    email = if (email.isNotEmpty()) email else null,
                    logoUri = selectedLogoUri?.toString()
                )
                suplierViewModel.insert(newSuplier)
                Toast.makeText(this, "Suplier $nama berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Mohon lengkapi data wajib (Nama, Alamat, Telp)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

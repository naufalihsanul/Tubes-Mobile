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

class EditKasirActivity : AppCompatActivity() {
    private lateinit var kasirViewModel: KasirViewModel
    private var kasir: Kasir? = null
    private var selectedFotoUri: Uri? = null

    private val pickFotoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedFotoUri = uri
            val ivFoto = findViewById<ImageView>(R.id.ivFotoKasirEdit)
            ivFoto.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_kasir)

        kasirViewModel = ViewModelProvider(this)[KasirViewModel::class.java]

        val etNama = findViewById<EditText>(R.id.etNamaKasirEdit)
        val etUsername = findViewById<EditText>(R.id.etUsernameKasirEdit)
        val ivFoto = findViewById<ImageView>(R.id.ivFotoKasirEdit)
        val btnPilihFoto = findViewById<Button>(R.id.btnPilihFotoKasirEdit)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanKasir)

        kasir = intent.getSerializableExtra("KASIR") as? Kasir

        kasir?.let {
            etNama.setText(it.nama)
            etUsername.setText(it.username)
            
            if (!it.fotoUri.isNullOrEmpty()) {
                selectedFotoUri = Uri.parse(it.fotoUri)
                ivFoto.setImageURI(selectedFotoUri)
            }
        }

        btnPilihFoto.setOnClickListener {
            pickFotoLauncher.launch("image/*")
        }

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val username = etUsername.text.toString().trim()

            if (nama.isNotEmpty() && username.isNotEmpty()) {
                kasir?.let { currentKasir ->
                    val kasirUpdated = currentKasir.copy(
                        nama = nama, 
                        username = username,
                        fotoUri = selectedFotoUri?.toString()
                    )
                    kasirViewModel.update(kasirUpdated)
                    Toast.makeText(this, "Kasir berhasil diupdate!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

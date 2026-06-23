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

class EditKasirActivity : AppCompatActivity() {
    private lateinit var kasirViewModel: KasirViewModel
    private var kasir: Kasir? = null
    private var selectedFotoUri: Uri? = null

    private val pickFotoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // Salin foto ke penyimpanan internal agar tetap bisa diakses setelah restart
            val savedUri = copyImageToInternalStorage(uri)
            if (savedUri != null) {
                selectedFotoUri = savedUri
                val ivFoto = findViewById<ImageView>(R.id.ivFotoKasirEdit)
                ivFoto.setImageURI(savedUri)
            }
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
                try {
                    ivFoto.setImageURI(selectedFotoUri)
                } catch (e: Exception) {
                    ivFoto.setImageResource(R.drawable.grouping)
                }
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

package com.example.pharmatic.suplier

import com.example.pharmatic.R

import com.example.pharmatic.model.Suplier

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pharmatic.viewmodel.SuplierViewModel
import java.io.File
import java.util.UUID

class EditSuplierActivity : AppCompatActivity() {
    private lateinit var suplierViewModel: SuplierViewModel
    private var suplier: Suplier? = null
    private var selectedLogoUri: Uri? = null

    private val pickLogoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // Salin gambar ke penyimpanan internal agar tetap bisa diakses setelah restart
            val savedUri = copyImageToInternalStorage(uri)
            if (savedUri != null) {
                selectedLogoUri = savedUri
                val ivLogo = findViewById<ImageView>(R.id.ivLogoSuplier)
                ivLogo.setImageURI(savedUri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_suplier)

        suplierViewModel = ViewModelProvider(this)[SuplierViewModel::class.java]

        val etNama = findViewById<EditText>(R.id.etNamaSuplier)
        val etAlamat = findViewById<EditText>(R.id.etAlamatSuplier)
        val etTelp = findViewById<EditText>(R.id.etTelpSuplier)
        val etEmail = findViewById<EditText>(R.id.etEmailSuplier)
        val ivLogo = findViewById<ImageView>(R.id.ivLogoSuplier)
        val btnPilihLogo = findViewById<Button>(R.id.btnPilihLogoSuplier)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanSuplier)

        suplier = intent.getSerializableExtra("SUPLIER") as? Suplier

        suplier?.let {
            etNama.setText(it.nama)
            etAlamat.setText(it.alamat)
            etTelp.setText(it.telepon)
            etEmail.setText(it.email ?: "")
            
            if (!it.logoUri.isNullOrEmpty()) {
                selectedLogoUri = Uri.parse(it.logoUri)
                try {
                    ivLogo.setImageURI(selectedLogoUri)
                } catch (e: Exception) {
                    ivLogo.setImageResource(R.drawable.delivery)
                }
            }
        }

        btnPilihLogo.setOnClickListener {
            pickLogoLauncher.launch("image/*")
        }

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val telp = etTelp.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (nama.isNotEmpty() && alamat.isNotEmpty() && telp.isNotEmpty()) {
                suplier?.let { currentSuplier ->
                    val suplierUpdated = currentSuplier.copy(
                        nama = nama, 
                        alamat = alamat, 
                        telepon = telp,
                        email = if (email.isNotEmpty()) email else null,
                        logoUri = selectedLogoUri?.toString()
                    )
                    suplierViewModel.update(suplierUpdated)
                    Toast.makeText(this, "Suplier berhasil diupdate!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Mohon lengkapi data wajib (Nama, Alamat, Telp)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun copyImageToInternalStorage(sourceUri: Uri): Uri? {
        return try {
            val inputStream = contentResolver.openInputStream(sourceUri) ?: return null
            val fileName = "suplier_${UUID.randomUUID()}.jpg"
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

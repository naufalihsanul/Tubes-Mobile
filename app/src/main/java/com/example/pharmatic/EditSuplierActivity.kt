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
import com.example.pharmatic.viewmodel.SuplierViewModel

class EditSuplierActivity : AppCompatActivity() {
    private lateinit var suplierViewModel: SuplierViewModel
    private var suplier: Suplier? = null
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
                ivLogo.setImageURI(selectedLogoUri)
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
}

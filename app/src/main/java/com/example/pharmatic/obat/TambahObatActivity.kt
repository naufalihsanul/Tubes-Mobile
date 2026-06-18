package com.example.pharmatic.obat

import com.example.pharmatic.model.Suplier

import com.example.pharmatic.R

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pharmatic.model.Obat
import com.example.pharmatic.viewmodel.ObatViewModel
import com.example.pharmatic.viewmodel.SuplierViewModel

import java.util.Calendar

class TambahObatActivity : AppCompatActivity() {
    private lateinit var obatViewModel: ObatViewModel
    private lateinit var suplierViewModel: SuplierViewModel
    private var selectedImageUri: Uri? = null
    private var listSuplier = emptyList<Suplier>()

    // Image Picker Launcher
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            val ivPreview = findViewById<ImageView>(R.id.ivPreviewObat)
            ivPreview.setImageURI(uri)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_obat)

        obatViewModel = ViewModelProvider(this)[ObatViewModel::class.java]
        suplierViewModel = ViewModelProvider(this)[SuplierViewModel::class.java]

        val ivPreview = findViewById<ImageView>(R.id.ivPreviewObat)
        val btnPilihGambar = findViewById<Button>(R.id.btnPilihGambar)

        val etNama = findViewById<EditText>(R.id.etNamaObatBaru)
        val spinnerJenis = findViewById<Spinner>(R.id.spinnerJenisObat)
        val spinnerSuplier = findViewById<Spinner>(R.id.spinnerSuplier)
        val etStok = findViewById<EditText>(R.id.etStokObatBaru)
        val etHarga = findViewById<EditText>(R.id.etHargaObatBaru)
        val etExpiredDate = findViewById<EditText>(R.id.etExpiredDate)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanObat)

        // Setup Spinner Jenis
        val jenisObatArray = arrayOf("Tablet", "Kapsul", "Sirup", "Salep", "Injeksi")
        val spinnerJenisAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, jenisObatArray)
        spinnerJenis.adapter = spinnerJenisAdapter

        // Setup Spinner Suplier
        suplierViewModel.allSuplier.observe(this) { supliers ->
            listSuplier = supliers
            val suplierNames = supliers.map { it.nama }.toMutableList()
            suplierNames.add(0, "-- Pilih Supplier --")
            val spinnerSuplierAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, suplierNames)
            spinnerSuplier.adapter = spinnerSuplierAdapter
        }

        // Action Pick Image
        btnPilihGambar.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }



        // Action DatePicker
        etExpiredDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val dateString = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etExpiredDate.setText(dateString)
            }, year, month, day)
            datePickerDialog.show()
        }

        // Action Simpan
        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val jenis = spinnerJenis.selectedItem.toString()
            val stokStr = etStok.text.toString().trim()
            val hargaStr = etHarga.text.toString().trim()
            val expiredDate = etExpiredDate.text.toString().trim()

            
            var selectedSuplierId: Int? = null
            if (spinnerSuplier.selectedItemPosition > 0) {
                // index 0 is "-- Pilih Supplier --", so subtract 1
                selectedSuplierId = listSuplier[spinnerSuplier.selectedItemPosition - 1].id
            }

            // Validasi Kosong
            if (nama.isEmpty() || stokStr.isEmpty() || hargaStr.isEmpty() || expiredDate.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data wajib!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi Angka & Minus
            val stok = stokStr.toIntOrNull()
            val harga = hargaStr.toIntOrNull()

            if (stok == null || stok < 0) {
                Toast.makeText(this, "Stok tidak valid (harus angka positif)!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (harga == null || harga <= 0) {
                Toast.makeText(this, "Harga tidak valid (harus angka positif)!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val obat = Obat(
                nama = nama,
                harga = harga,
                stok = stok,
                jenis = jenis,
                imageUri = selectedImageUri?.toString(),
                expiredDate = expiredDate,

                suplierId = selectedSuplierId
            )

            obatViewModel.insert(obat)
            Toast.makeText(this, "Obat $nama berhasil disimpan!", Toast.LENGTH_SHORT).show()
            finish() // Kembali ke dashboard admin setelah simpan
        }
    }
}

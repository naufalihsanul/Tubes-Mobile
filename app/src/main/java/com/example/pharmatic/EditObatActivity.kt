package com.example.pharmatic

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
import com.example.pharmatic.obat.Obat
import com.example.pharmatic.viewmodel.ObatViewModel
import com.example.pharmatic.viewmodel.SuplierViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import java.util.Calendar

class EditObatActivity : AppCompatActivity() {
    private lateinit var obatViewModel: ObatViewModel
    private lateinit var suplierViewModel: SuplierViewModel
    private var obat: Obat? = null
    private var selectedImageUri: Uri? = null
    private var listSuplier = emptyList<Suplier>()

    // Image Picker Launcher
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            val ivPreview = findViewById<ImageView>(R.id.ivPreviewObatEdit)
            ivPreview.setImageURI(uri)
        }
    }

    // Barcode Scanner Launcher
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            findViewById<EditText>(R.id.etBarcodeEdit).setText(result.contents)
        } else {
            Toast.makeText(this, "Scan dibatalkan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_obat)

        obatViewModel = ViewModelProvider(this)[ObatViewModel::class.java]
        suplierViewModel = ViewModelProvider(this)[SuplierViewModel::class.java]

        val ivPreview = findViewById<ImageView>(R.id.ivPreviewObatEdit)
        val btnPilihGambar = findViewById<Button>(R.id.btnPilihGambarEdit)
        val etBarcode = findViewById<EditText>(R.id.etBarcodeEdit)
        val btnScanBarcode = findViewById<Button>(R.id.btnScanBarcodeEdit)
        val etNama = findViewById<EditText>(R.id.etNamaObatEdit)
        val spinnerJenis = findViewById<Spinner>(R.id.spinnerJenisObatEdit)
        val spinnerSuplier = findViewById<Spinner>(R.id.spinnerSuplierEdit)
        val etStok = findViewById<EditText>(R.id.etStokObatEdit)
        val etHarga = findViewById<EditText>(R.id.etHargaObatEdit)
        val etExpiredDate = findViewById<EditText>(R.id.etExpiredDateEdit)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanObat)

        // Setup Spinner Jenis
        val jenisObatArray = arrayOf("Tablet", "Kapsul", "Sirup", "Salep", "Injeksi")
        val spinnerJenisAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, jenisObatArray)
        spinnerJenis.adapter = spinnerJenisAdapter

        // Load Data
        obat = intent.getSerializableExtra("OBAT") as? Obat

        // Setup Spinner Suplier
        suplierViewModel.allSuplier.observe(this) { supliers ->
            listSuplier = supliers
            val suplierNames = supliers.map { it.nama }.toMutableList()
            suplierNames.add(0, "-- Pilih Supplier --")
            val spinnerSuplierAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, suplierNames)
            spinnerSuplier.adapter = spinnerSuplierAdapter

            // Pre-select Suplier if editing
            obat?.suplierId?.let { sId ->
                val index = listSuplier.indexOfFirst { it.id == sId }
                if (index != -1) {
                    spinnerSuplier.setSelection(index + 1) // +1 because index 0 is "-- Pilih Supplier --"
                }
            }
        }

        obat?.let {
            etNama.setText(it.nama)
            etStok.setText(it.stok.toString())
            etHarga.setText(it.harga.toString())
            etBarcode.setText(it.barcode ?: "")
            etExpiredDate.setText(it.expiredDate ?: "")
            
            // Set Spinner Selection
            val spinnerPosition = spinnerJenisAdapter.getPosition(it.jenis)
            if (spinnerPosition >= 0) {
                spinnerJenis.setSelection(spinnerPosition)
            }

            // Set Image
            if (!it.imageUri.isNullOrEmpty()) {
                selectedImageUri = Uri.parse(it.imageUri)
                ivPreview.setImageURI(selectedImageUri)
            }
        }

        // Action Pick Image
        btnPilihGambar.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Action Scan Barcode
        btnScanBarcode.setOnClickListener {
            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
            options.setPrompt("Arahkan kamera ke barcode obat")
            options.setCameraId(0)
            options.setBeepEnabled(true)
            options.setBarcodeImageEnabled(true)
            barcodeLauncher.launch(options)
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
            val barcode = etBarcode.text.toString().trim()

            var selectedSuplierId: Int? = null
            if (spinnerSuplier.selectedItemPosition > 0) {
                // index 0 is "-- Pilih Supplier --", so subtract 1
                selectedSuplierId = listSuplier[spinnerSuplier.selectedItemPosition - 1].id
            }

            if (nama.isEmpty() || stokStr.isEmpty() || hargaStr.isEmpty() || expiredDate.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data wajib!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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

            obat?.let { currentObat ->
                val obatUpdated = currentObat.copy(
                    nama = nama, 
                    stok = stok, 
                    harga = harga,
                    jenis = jenis,
                    imageUri = selectedImageUri?.toString(),
                    expiredDate = expiredDate,
                    barcode = barcode,
                    suplierId = selectedSuplierId
                )
                obatViewModel.update(obatUpdated)
                Toast.makeText(this, "Obat berhasil diupdate!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

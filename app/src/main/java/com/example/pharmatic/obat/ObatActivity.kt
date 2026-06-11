package com.example.pharmatic.obat

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.R
import com.example.pharmatic.keranjang.KeranjangActivity
import com.example.pharmatic.keranjang.KeranjangManager
import com.example.pharmatic.viewmodel.ObatViewModel
import android.widget.ImageButton
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class ObatActivity : AppCompatActivity() {

    private lateinit var rvObat: RecyclerView
    private lateinit var obatViewModel: ObatViewModel
    private var fullObatList: List<Obat> = emptyList()
    private lateinit var adapter: ObatAdapter

    // Barcode Scanner Launcher
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val scannedBarcode = result.contents
            val obatDitemukan = fullObatList.find { it.barcode == scannedBarcode }
            
            if (obatDitemukan != null) {
                // Langsung tambahkan ke keranjang
                KeranjangManager.daftarKeranjang.add(obatDitemukan)
                KeranjangManager.saveCart(this)
                Toast.makeText(this, "✅ ${obatDitemukan.nama} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "❌ Obat dengan barcode $scannedBarcode tidak ditemukan", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_obat)

        rvObat = findViewById(R.id.rvObat)
        rvObat.layoutManager = LinearLayoutManager(this)

        adapter = ObatAdapter(emptyList())
        rvObat.adapter = adapter

        obatViewModel = ViewModelProvider(this)[ObatViewModel::class.java]
        obatViewModel.allObat.observe(this) { obatList ->
            fullObatList = obatList
            adapter.updateData(obatList)
        }

        val etCari = findViewById<EditText>(R.id.etCari)

        etCari.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString().lowercase()
                val hasilFilter = fullObatList.filter {
                    it.nama.lowercase().contains(keyword)
                }
                adapter.updateData(hasilFilter)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Event Scan Barcode Kasir
        val btnScanBarcodeObat = findViewById<ImageButton>(R.id.btnScanBarcodeObat)
        btnScanBarcodeObat.setOnClickListener {
            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
            options.setPrompt("Arahkan kamera ke barcode obat")
            options.setCameraId(0) // Kamera belakang
            options.setBeepEnabled(true)
            options.setBarcodeImageEnabled(true)
            barcodeLauncher.launch(options)
        }

        val btnKeranjang = findViewById<Button>(R.id.btnKeranjang)
        btnKeranjang.setOnClickListener {
            if (KeranjangManager.daftarKeranjang.isEmpty()) {
                Toast.makeText(
                    this,
                    "⚠️ Silakan pilih obat terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            startActivity(Intent(this, KeranjangActivity::class.java))
        }
    }
}

package com.example.pharmatic

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import com.example.pharmatic.keranjang.KeranjangManager
import com.example.pharmatic.obat.Obat
import com.example.pharmatic.pembayaran.PembayaranActivity
import com.example.pharmatic.viewmodel.ObatViewModel

class TransaksiActivity : AppCompatActivity() {

    private var totalHarga = 0
    private var totalItem = 0
    private lateinit var tvTotalHarga: TextView
    private lateinit var tvTotalItem: TextView
    private lateinit var obatViewModel: ObatViewModel
    private lateinit var adapter: TransaksiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)

        tvTotalHarga = findViewById(R.id.tvTotalHarga)
        tvTotalItem = findViewById(R.id.tvTotalItem)
        val btnBayar = findViewById<Button>(R.id.btnBayar)
        val rvPilihObat = findViewById<RecyclerView>(R.id.rvPilihObat)

        // Setup RecyclerView
        rvPilihObat.layoutManager = LinearLayoutManager(this)

        adapter = TransaksiAdapter(emptyList()) { obat ->
            tambahKeKeranjang(obat)
        }
        rvPilihObat.adapter = adapter

        obatViewModel = ViewModelProvider(this)[ObatViewModel::class.java]
        obatViewModel.allObat.observe(this) { obatList ->
            adapter.updateData(obatList)
        }

        btnBayar.setOnClickListener {
            if (KeranjangManager.daftarKeranjang.isNotEmpty()) {
                val intent = Intent(this, PembayaranActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Keranjang masih kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun tambahKeKeranjang(obat: Obat) {
        totalItem += 1
        totalHarga += obat.harga
        KeranjangManager.daftarKeranjang.add(obat)
        updateUI()
    }

    private fun updateUI() {
        tvTotalItem.text = totalItem.toString()
        tvTotalHarga.text = "Rp $totalHarga"
    }
}

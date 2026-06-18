package com.example.pharmatic.keranjang

import com.example.pharmatic.model.KeranjangItem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.R
import com.example.pharmatic.pembayaran.PembayaranActivity

class KeranjangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        val rvKeranjang = findViewById<RecyclerView>(R.id.rvKeranjang)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnBayar = findViewById<Button>(R.id.btnBayar)

        rvKeranjang.layoutManager = LinearLayoutManager(this)
        
        val adapter = KeranjangAdapter(emptyList()) {
            loadData(tvTotal, rvKeranjang)
        }
        rvKeranjang.adapter = adapter

        loadData(tvTotal, rvKeranjang)

        btnBayar.setOnClickListener {
            startActivity(Intent(this, PembayaranActivity::class.java))
        }
    }

    private fun loadData(tvTotal: TextView, rvKeranjang: RecyclerView) {
        val grouped = KeranjangManager.daftarKeranjang.groupBy { it.nama }
        val data = mutableListOf<KeranjangItem>()
        var total = 0

        for ((nama, daftar) in grouped) {
            val qty = daftar.size
            val subtotal = qty * daftar[0].harga
            total += subtotal
            data.add(KeranjangItem(nama, qty, subtotal))
        }

        tvTotal.text = "TOTAL : Rp $total"
        (rvKeranjang.adapter as KeranjangAdapter).updateData(data)
    }
}

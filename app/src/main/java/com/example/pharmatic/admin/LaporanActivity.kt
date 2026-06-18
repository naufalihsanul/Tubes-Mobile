package com.example.pharmatic.admin

import com.example.pharmatic.history.HistoryAdapter

import com.example.pharmatic.R

import com.example.pharmatic.model.Transaksi

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.viewmodel.TransaksiViewModel

class LaporanActivity : AppCompatActivity() {
    private lateinit var adapter: HistoryAdapter
    private lateinit var tvTotalPendapatan: TextView
    private lateinit var tvTotalTransaksi: TextView
    private lateinit var transaksiViewModel: TransaksiViewModel
    private var totalTransaksiCount = 0
    private var totalPendapatanFormatted = "Rp 0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)

        tvTotalPendapatan = findViewById(R.id.tvTotalPendapatan)
        tvTotalTransaksi = findViewById(R.id.tvTotalTransaksi)
        
        val rvLaporanRiwayat = findViewById<RecyclerView>(R.id.rvLaporanRiwayat)
        rvLaporanRiwayat.layoutManager = LinearLayoutManager(this)
        
        adapter = HistoryAdapter(emptyList())
        rvLaporanRiwayat.adapter = adapter

        transaksiViewModel = ViewModelProvider(this)[TransaksiViewModel::class.java]

        transaksiViewModel.allTransaksi.observe(this) { list ->
            adapter.updateData(list)
            totalTransaksiCount = list.size
            tvTotalTransaksi.text = "$totalTransaksiCount Transaksi"
            
            // Calculate total revenue asynchronously
            transaksiViewModel.hitungTotalPendapatan { formatted ->
                totalPendapatanFormatted = formatted
                tvTotalPendapatan.text = formatted
            }
        }
        
        val btnCetak = findViewById<Button>(R.id.btnCetakLaporan)
        btnCetak.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val textToShare = "Laporan Penjualan PharmaTic\n" +
                              "Total Transaksi: $totalTransaksiCount\n" +
                              "Total Pendapatan: $totalPendapatanFormatted"
            shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare)
            startActivity(Intent.createChooser(shareIntent, "Cetak Laporan via..."))
        }
    }
}

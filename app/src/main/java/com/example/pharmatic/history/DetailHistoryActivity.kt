package com.example.pharmatic.history

import com.example.pharmatic.DashboardActivity

import com.example.pharmatic.R

import com.example.pharmatic.model.Transaksi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.viewmodel.TransaksiViewModel

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var transaksiViewModel: TransaksiViewModel
    private lateinit var adapter: DetailHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_history)

        transaksiViewModel = ViewModelProvider(this)[TransaksiViewModel::class.java]

        val tvId = findViewById<TextView>(R.id.tvDetailId)
        val tvTanggal = findViewById<TextView>(R.id.tvDetailTanggal)
        val tvItem = findViewById<TextView>(R.id.tvDetailItem)
        val tvTotal = findViewById<TextView>(R.id.tvDetailTotal)
        val rvDetail = findViewById<RecyclerView>(R.id.rvDetailHistory)

        adapter = DetailHistoryAdapter()
        rvDetail.layoutManager = LinearLayoutManager(this)
        rvDetail.adapter = adapter

        val transaksi = intent.getSerializableExtra("TRANSAKSI") as? Transaksi

        transaksi?.let {
            tvId.text = it.idTransaksi
            tvTanggal.text = "Tanggal: ${it.tanggal}\nKasir: ${it.kasirNama ?: "-"}"
            tvItem.text = "Total Item: ${it.totalItem}"
            tvTotal.text = "Rp %,d".format(it.totalHarga).replace(',', '.')

            transaksiViewModel.getDetailsByTransaksi(it.idTransaksi).observe(this) { details ->
                adapter.updateData(details)
            }
        }
        
        val btnCetakStruk = findViewById<android.widget.Button>(R.id.btnCetakStruk)
        btnCetakStruk.setOnClickListener {
            android.widget.Toast.makeText(this, "Struk berhasil dicetak", android.widget.Toast.LENGTH_SHORT).show()
            val intent = android.content.Intent(this, DashboardActivity::class.java)
            intent.flags = android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}

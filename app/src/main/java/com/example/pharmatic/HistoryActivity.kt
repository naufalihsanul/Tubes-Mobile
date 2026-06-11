package com.example.pharmatic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.viewmodel.TransaksiViewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var adapter: HistoryAdapter
    private lateinit var transaksiViewModel: TransaksiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
        rvHistory.layoutManager = LinearLayoutManager(this)

        adapter = HistoryAdapter(emptyList())
        rvHistory.adapter = adapter

        transaksiViewModel = ViewModelProvider(this)[TransaksiViewModel::class.java]
        transaksiViewModel.allTransaksi.observe(this) { list ->
            adapter.updateData(list)
        }
    }
}

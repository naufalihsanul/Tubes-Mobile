package com.example.pharmatic.kasir

import com.example.pharmatic.R

import com.example.pharmatic.model.Kasir

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.viewmodel.KasirViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class KelolaKasirActivity : AppCompatActivity() {
    private lateinit var rvKasir: RecyclerView
    private lateinit var adapter: KasirAdapter
    private lateinit var kasirViewModel: KasirViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelola_kasir)

        rvKasir = findViewById(R.id.rvKasir)
        rvKasir.layoutManager = LinearLayoutManager(this)

        kasirViewModel = ViewModelProvider(this)[KasirViewModel::class.java]

        adapter = KasirAdapter(emptyList()) { kasir ->
            kasirViewModel.delete(kasir)
        }
        rvKasir.adapter = adapter

        kasirViewModel.allKasir.observe(this) { list ->
            adapter.updateData(list)
        }
        
        val fabTambah = findViewById<FloatingActionButton>(R.id.fabTambahKasir)
        fabTambah.setOnClickListener {
            startActivity(Intent(this, TambahKasirActivity::class.java))
        }
    }
}

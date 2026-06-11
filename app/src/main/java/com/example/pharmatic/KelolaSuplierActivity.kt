package com.example.pharmatic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.viewmodel.SuplierViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class KelolaSuplierActivity : AppCompatActivity() {
    private lateinit var rvSuplier: RecyclerView
    private lateinit var adapter: SuplierAdapter
    private lateinit var suplierViewModel: SuplierViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelola_suplier)

        rvSuplier = findViewById(R.id.rvSuplier)
        rvSuplier.layoutManager = LinearLayoutManager(this)

        suplierViewModel = ViewModelProvider(this)[SuplierViewModel::class.java]

        adapter = SuplierAdapter(emptyList()) { suplier ->
            suplierViewModel.delete(suplier)
        }
        rvSuplier.adapter = adapter

        suplierViewModel.allSuplier.observe(this) { list ->
            adapter.updateData(list)
        }

        val fabTambah = findViewById<FloatingActionButton>(R.id.fabTambahSuplier)
        fabTambah.setOnClickListener {
            startActivity(Intent(this, TambahSuplierActivity::class.java))
        }
    }
}

package com.example.pharmatic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmatic.viewmodel.ObatViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class KelolaObatActivity : AppCompatActivity() {

    private lateinit var rvObat: RecyclerView
    private lateinit var adapter: ObatAdminAdapter
    private lateinit var obatViewModel: ObatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelola_obat)

        rvObat = findViewById(R.id.rvKelolaObat)
        rvObat.layoutManager = LinearLayoutManager(this)

        adapter = ObatAdminAdapter(emptyList()) { obat ->
            obatViewModel.delete(obat)
        }
        rvObat.adapter = adapter

        obatViewModel = ViewModelProvider(this)[ObatViewModel::class.java]
        
        val emptyState = findViewById<android.view.View>(R.id.layoutEmptyState)

        obatViewModel.allObat.observe(this) { obatList ->
            adapter.updateData(obatList)
            if (obatList.isEmpty()) {
                rvObat.visibility = android.view.View.GONE
                emptyState.visibility = android.view.View.VISIBLE
            } else {
                rvObat.visibility = android.view.View.VISIBLE
                emptyState.visibility = android.view.View.GONE
            }
        }

        val fabTambah = findViewById<FloatingActionButton>(R.id.fabTambahObat)
        fabTambah.setOnClickListener {
            startActivity(Intent(this, TambahObatActivity::class.java))
        }
    }
}

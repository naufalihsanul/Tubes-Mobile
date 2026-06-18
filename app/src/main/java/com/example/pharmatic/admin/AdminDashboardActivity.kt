package com.example.pharmatic.admin

import com.example.pharmatic.auth.LoginActivity

import com.example.pharmatic.suplier.KelolaSuplierActivity

import com.example.pharmatic.kasir.KelolaKasirActivity

import com.example.pharmatic.obat.KelolaObatActivity

import com.example.pharmatic.R

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.pharmatic.data.SessionManager
import com.example.pharmatic.viewmodel.ObatViewModel

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var obatViewModel: ObatViewModel
    private var currentNotifDetail: String = "Belum ada data."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        sessionManager = SessionManager(this)

        val cardKelolaObat = findViewById<CardView>(R.id.cardKelolaObat)
        val cardKasir = findViewById<CardView>(R.id.cardKelolaKasir)
        val btnLogout = findViewById<Button>(R.id.btnAdminLogout)
        val cardSuplier = findViewById<CardView>(R.id.cardKelolaSuplier)
        val cardLaporan = findViewById<CardView>(R.id.cardLaporan)
        val layoutNotifAdmin = findViewById<android.widget.LinearLayout>(R.id.layoutNotifAdmin)

        obatViewModel = ViewModelProvider(this)[ObatViewModel::class.java]
        
        obatViewModel.allObat.observe(this) { obatList ->
            val obatHabis = obatList.filter { it.stok <= 10 }
            val tvNotifTextAdmin = findViewById<TextView>(R.id.tvNotifTextAdmin)
            
            if (obatHabis.isNotEmpty()) {
                val namaObat = obatHabis.joinToString("\n- ") { "${it.nama} (Sisa: ${it.stok})" }
                val pesanSingkat = "Peringatan: ${obatHabis.size} jenis obat menipis!"
                tvNotifTextAdmin.text = pesanSingkat
                tvNotifTextAdmin.setTextColor(android.graphics.Color.parseColor("#E74C3C"))
                currentNotifDetail = "Daftar obat yang perlu segera ditambah stoknya:\n\n- $namaObat"
            } else {
                tvNotifTextAdmin.text = "Notifikasi: Stok semua obat dalam kondisi aman."
                tvNotifTextAdmin.setTextColor(android.graphics.Color.parseColor("#2ECC71"))
                currentNotifDetail = "Semua stok obat masih di atas 10. Tidak ada tindakan yang diperlukan saat ini."
            }
        }
        
        layoutNotifAdmin.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Detail Notifikasi")
                .setMessage(currentNotifDetail)
                .setPositiveButton("Kelola Obat") { _, _ ->
                    val intent = Intent(this, KelolaObatActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Tutup", null)
                .show()
        }

        cardKelolaObat.setOnClickListener {
            val intent = Intent(this, KelolaObatActivity::class.java)
            startActivity(intent)
        }

        cardKasir.setOnClickListener {
            val intent = Intent(this, KelolaKasirActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar dari panel admin?")
                .setPositiveButton("Keluar") { _, _ ->
                    sessionManager.logout()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Admin Logout", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Batal", null)
                .show()
        }


        cardSuplier.setOnClickListener {
            val intent = Intent(this, KelolaSuplierActivity::class.java)
            startActivity(intent)
        }

        cardLaporan.setOnClickListener {
            val intent = Intent(this, LaporanActivity::class.java)
            startActivity(intent)
        }
    }
}

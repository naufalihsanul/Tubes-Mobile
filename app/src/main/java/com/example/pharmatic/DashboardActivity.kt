package com.example.pharmatic

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.pharmatic.data.SessionManager
import com.example.pharmatic.obat.Obat
import com.example.pharmatic.obat.ObatActivity
import com.example.pharmatic.viewmodel.ObatViewModel
import com.example.pharmatic.viewmodel.TransaksiViewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvTotalObat: TextView
    private lateinit var tvTotalTransaksi: TextView
    private lateinit var sessionManager: SessionManager
    private lateinit var obatViewModel: ObatViewModel
    private lateinit var transaksiViewModel: TransaksiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sessionManager = SessionManager(this)
        obatViewModel = ViewModelProvider(this)[ObatViewModel::class.java]
        transaksiViewModel = ViewModelProvider(this)[TransaksiViewModel::class.java]
        
        tvTotalObat = findViewById(R.id.tvTotalObatDash)
        tvTotalTransaksi = findViewById(R.id.tvTotalTransaksiDash)

        // Observe LiveData for real-time counts
        obatViewModel.allObat.observe(this) { obatList ->
            tvTotalObat.text = obatList.size.toString()
            cekStokObat(obatList)
        }

        transaksiViewModel.allTransaksi.observe(this) { transaksiList ->
            tvTotalTransaksi.text = transaksiList.size.toString()
        }

        // Inisialisasi CardView dari layout
        val cardObat = findViewById<CardView>(R.id.cardObat)
        val cardHistory = findViewById<CardView>(R.id.cardHistory)
        val cardLogout = findViewById<CardView>(R.id.cardLogout)
        val layoutNotifKasir = findViewById<android.widget.LinearLayout>(R.id.layoutNotifKasir)

        // Event Klik Notifikasi
        layoutNotifKasir.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Detail Notifikasi")
                .setMessage(currentNotifDetail)
                .setPositiveButton("Lihat Daftar Obat") { _, _ ->
                    val intent = Intent(this, ObatActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Tutup", null)
                .show()
        }

        // Event Klik Stok Obat
        cardObat.setOnClickListener {
            val intent = Intent(this, ObatActivity::class.java)
            startActivity(intent)
        }

        // Event Klik Stok Obat
        cardObat.setOnClickListener {
            val intent = Intent(this, ObatActivity::class.java)
            startActivity(intent)
        }

        // Event Klik Riwayat
        cardHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        // Event Klik Logout (Kembali ke Login)
        cardLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Keluar") { _, _ ->
                    sessionManager.logout()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Berhasil Keluar", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
        
        createNotificationChannel()
    }

    override fun onResume() {
        super.onResume()
    }

    private var currentNotifDetail: String = "Belum ada data."

    private fun cekStokObat(obatList: List<Obat>) {
        val obatHabis = obatList.filter { it.stok <= 10 }
        val tvNotifText = findViewById<TextView>(R.id.tvNotifText)

        if (obatHabis.isNotEmpty()) {
            val namaObat = obatHabis.joinToString("\n- ") { "${it.nama} (Sisa: ${it.stok})" }
            val pesanSingkat = "Peringatan: ${obatHabis.size} jenis obat menipis!"
            val pesanPanjang = "Peringatan: Stok obat menipis (Sisa <= 10) untuk: ${obatHabis.joinToString(", ") { it.nama }}"
            tvNotifText.text = pesanSingkat
            tvNotifText.setTextColor(android.graphics.Color.parseColor("#E74C3C")) // Merah/Bahaya
            
            currentNotifDetail = "Daftar obat yang perlu segera ditambah stoknya:\n\n- $namaObat"
            tampilkanNotifikasi("Stok Obat Menipis!", pesanPanjang)
        } else {
            tvNotifText.text = "Notifikasi: Stok semua obat dalam kondisi aman."
            tvNotifText.setTextColor(android.graphics.Color.parseColor("#2ECC71")) // Hijau/Aman
            currentNotifDetail = "Semua stok obat masih di atas 10. Tidak ada tindakan yang diperlukan saat ini."
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Stok Obat"
            val descriptionText = "Notifikasi stok obat menipis"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("STOK_CHANNEL", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun tampilkanNotifikasi(title: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
                return
            }
        }

        val builder = NotificationCompat.Builder(this, "STOK_CHANNEL")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }
}

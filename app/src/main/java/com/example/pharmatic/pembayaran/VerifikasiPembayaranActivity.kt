package com.example.pharmatic.pembayaran

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmatic.R
import com.example.pharmatic.struk.StrukActivity

class VerifikasiPembayaranActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifikasi_pembayaran)

        val total = intent.getIntExtra("TOTAL", 0)
        val bayar = intent.getIntExtra("BAYAR", 0)
        val kembalian = intent.getIntExtra("KEMBALIAN", 0)

        val tvTotal = findViewById<TextView>(R.id.tvVerifTotal)
        val tvBayar = findViewById<TextView>(R.id.tvVerifBayar)
        val tvKembalian = findViewById<TextView>(R.id.tvVerifKembalian)

        tvTotal.text = "Rp %,d".format(total).replace(',', '.')
        tvBayar.text = "Rp %,d".format(bayar).replace(',', '.')
        tvKembalian.text = "Rp %,d".format(kembalian).replace(',', '.')

        val btnBatal = findViewById<Button>(R.id.btnBatalVerif)
        val btnKonfirmasi = findViewById<Button>(R.id.btnKonfirmasi)

        btnBatal.setOnClickListener {
            finish() // Kembali ke halaman input nominal
        }

        btnKonfirmasi.setOnClickListener {
            val intent = Intent(this, StrukActivity::class.java)
            intent.putExtra("TOTAL", total)
            intent.putExtra("BAYAR", bayar)
            intent.putExtra("KEMBALIAN", kembalian)
            startActivity(intent)
            finish()
        }
    }
}

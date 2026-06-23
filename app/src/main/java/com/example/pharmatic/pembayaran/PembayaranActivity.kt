package com.example.pharmatic.pembayaran

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmatic.R
import com.example.pharmatic.keranjang.KeranjangManager
import com.example.pharmatic.struk.StrukActivity

class PembayaranActivity : AppCompatActivity() {

    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)

        val tvTotal = findViewById<TextView>(R.id.tvTotalBayar)
        val tvKembalian = findViewById<TextView>(R.id.tvKembalian)
        val etNominal = findViewById<EditText>(R.id.etNominal)
        val btnHitung = findViewById<Button>(R.id.btnHitung)
        val btnBayar = findViewById<Button>(R.id.btnBayar)

        // Sembunyikan tombol hitung karena sekarang otomatis
        if (btnHitung != null) {
            btnHitung.visibility = View.GONE
        }

        for (obat in KeranjangManager.daftarKeranjang) {
            total += obat.harga
        }

        tvTotal.text = "Rp $total"

        etNominal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val bayarStr = s.toString()
                if (bayarStr.isNotEmpty()) {
                    try {
                        val nominal = bayarStr.toInt()
                        if (nominal >= total) {
                            val kembalian = nominal - total
                            tvKembalian.text = "Rp $kembalian"
                            tvKembalian.setTextColor(resources.getColor(R.color.emerald_dark))
                        } else {
                            tvKembalian.text = "Uang Kurang"
                            tvKembalian.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                        }
                    } catch (e: NumberFormatException) {
                        tvKembalian.text = "Rp 0"
                        tvKembalian.setTextColor(resources.getColor(R.color.emerald_dark))
                    }
                } else {
                    tvKembalian.text = "Rp 0"
                    tvKembalian.setTextColor(resources.getColor(R.color.emerald_dark))
                }
            }
        })

        btnBayar.setOnClickListener {
            val bayar = etNominal.text.toString()

            if (bayar.isEmpty()) {
                Toast.makeText(this, "Masukkan nominal pembayaran terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val nominal = bayar.toInt()

                if (nominal < total) {
                    Toast.makeText(this, "Pembayaran belum mencukupi", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val kembalian = nominal - total

                val intent = Intent(this, VerifikasiPembayaranActivity::class.java)
                intent.putExtra("TOTAL", total)
                intent.putExtra("BAYAR", nominal)
                intent.putExtra("KEMBALIAN", kembalian)

                startActivity(intent)
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Nominal tidak valid", Toast.LENGTH_SHORT).show()
            }
        }

        // --- Logika Midtrans QRIS ---
        val btnBayarQris = findViewById<Button>(R.id.btnBayarQris)
        val cvQrisContainer = findViewById<androidx.cardview.widget.CardView>(R.id.cvQrisContainer)
        val ivQrCode = findViewById<android.widget.ImageView>(R.id.ivQrCode)
        val pbLoadingQr = findViewById<android.widget.ProgressBar>(R.id.pbLoadingQr)
        val btnSelesaiQris = findViewById<Button>(R.id.btnSelesaiQris)

        btnBayarQris.setOnClickListener {
            // Sembunyikan input tunai
            etNominal.visibility = View.GONE
            btnBayar.visibility = View.GONE
            btnBayarQris.visibility = View.GONE

            // Tampilkan container
            cvQrisContainer.visibility = View.VISIBLE
            pbLoadingQr.visibility = View.VISIBLE
            ivQrCode.visibility = View.GONE

            val orderId = "TRX-" + System.currentTimeMillis()
            
            // Panggil API Midtrans
            val request = com.example.pharmatic.api.MidtransRequest(
                transactionDetails = com.example.pharmatic.api.TransactionDetails(
                    orderId = orderId,
                    grossAmount = total
                )
            )

            com.example.pharmatic.api.RetrofitClient.instance.createTransaction(request)
                .enqueue(object : retrofit2.Callback<com.example.pharmatic.api.MidtransResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<com.example.pharmatic.api.MidtransResponse>,
                        response: retrofit2.Response<com.example.pharmatic.api.MidtransResponse>
                    ) {
                        pbLoadingQr.visibility = View.GONE
                        if (response.isSuccessful) {
                            val redirectUrl = response.body()?.redirectUrl
                            if (redirectUrl != null) {
                                // Buka URL Snap di Browser
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = android.net.Uri.parse(redirectUrl)
                                startActivity(intent)
                                
                                Toast.makeText(this@PembayaranActivity, "Silakan selesaikan pembayaran di browser lalu klik Selesai", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@PembayaranActivity, "Gagal mendapatkan URL Pembayaran", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@PembayaranActivity, "Error API: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<com.example.pharmatic.api.MidtransResponse>, t: Throwable) {
                        pbLoadingQr.visibility = View.GONE
                        Toast.makeText(this@PembayaranActivity, "Koneksi Gagal: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        btnSelesaiQris.setOnClickListener {
            // Anggap pembayaran berhasil karena menggunakan MVP tanpa webhook backend
            val intent = Intent(this, VerifikasiPembayaranActivity::class.java)
            intent.putExtra("TOTAL", total)
            intent.putExtra("BAYAR", total) // Pembayaran pas karena QRIS
            intent.putExtra("KEMBALIAN", 0)

            startActivity(intent)
        }
    }
}

package com.example.pharmatic.struk

import com.example.pharmatic.model.TransaksiDetail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pharmatic.R
import com.example.pharmatic.keranjang.KeranjangManager
import com.example.pharmatic.model.Transaksi
import com.example.pharmatic.viewmodel.ObatViewModel
import com.example.pharmatic.viewmodel.TransaksiViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StrukActivity : AppCompatActivity() {

    private lateinit var obatViewModel: ObatViewModel
    private lateinit var transaksiViewModel: TransaksiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_struk)

        obatViewModel = ViewModelProvider(this)[ObatViewModel::class.java]
        transaksiViewModel = ViewModelProvider(this)[TransaksiViewModel::class.java]

        val tvStruk = findViewById<TextView>(R.id.tvStruk)
        val btnSelesai = findViewById<Button>(R.id.btnSelesai)

        val total = intent.getIntExtra("TOTAL", 0)
        val bayar = intent.getIntExtra("BAYAR", 0)
        val kembalian = intent.getIntExtra("KEMBALIAN", 0)

        val grouped = KeranjangManager.daftarKeranjang.groupBy { it.nama }

        val tanggal = SimpleDateFormat("dd-MM-yyyy", Locale("id", "ID")).format(Date())
        val jam = SimpleDateFormat("HH:mm", Locale("id", "ID")).format(Date())

        var isiStruk = ""
        isiStruk += "🏥 PHARMATIC APOTEK\n"
        isiStruk += "================================\n\n"
        isiStruk += String.format("%-18s %-5s %-10s\n", "Nama Obat", "Qty", "Total")
        isiStruk += "--------------------------------\n"

        for ((nama, daftar) in grouped) {
            val qty = daftar.size
            val subtotal = qty * daftar[0].harga
            isiStruk += String.format(
                "%-18s %-5d %-10d\n",
                nama.take(18),
                qty,
                subtotal
            )
        }

        isiStruk += "\n--------------------------------\n\n"
        isiStruk += String.format("%-12s : Rp %,d\n", "TOTAL", total)
        isiStruk += String.format("%-12s : Rp %,d\n", "BAYAR", bayar)
        isiStruk += String.format("%-12s : Rp %,d\n", "KEMBALI", kembalian)
        isiStruk += "\n--------------------------------\n"
        isiStruk += "Tanggal : $tanggal\n"
        isiStruk += "Jam     : $jam\n"
        isiStruk += "\n================================\n\n"
        isiStruk += "✓ Transaksi Berhasil\n\n"
        isiStruk += "Terima Kasih !\n"
        isiStruk += "Semoga Lekas Sembuh !"

        tvStruk.text = isiStruk

        btnSelesai.setOnClickListener {
            val idTrx = "#TRX-${System.currentTimeMillis()}"
            val sessionManager = com.example.pharmatic.data.SessionManager(this)
            
            val transaksiBaru = Transaksi(
                idTransaksi = idTrx,
                tanggal = "$tanggal, $jam",
                totalItem = KeranjangManager.daftarKeranjang.size,
                totalHarga = total,
                kasirNama = sessionManager.getUsername()
            )
            
            val detailsList = mutableListOf<com.example.pharmatic.model.TransaksiDetail>()
            for ((nama, daftar) in grouped) {
                val qty = daftar.size
                val subtotal = qty * daftar[0].harga
                detailsList.add(
                    com.example.pharmatic.model.TransaksiDetail(
                        transaksiId = idTrx,
                        namaObat = nama,
                        hargaSatuan = daftar[0].harga,
                        qty = qty,
                        subtotal = subtotal
                    )
                )
            }
            
            transaksiViewModel.insertWithDetails(transaksiBaru, detailsList)

            // Simpan ke Firebase Firestore
            val firestore = com.google.firebase.firestore.FirebaseFirestore.getInstance()
            val trxMap = hashMapOf(
                "idTransaksi" to transaksiBaru.idTransaksi,
                "tanggal" to transaksiBaru.tanggal,
                "totalItem" to transaksiBaru.totalItem,
                "totalHarga" to transaksiBaru.totalHarga,
                "kasirNama" to (transaksiBaru.kasirNama ?: "Unknown"),
                "details" to detailsList.map { detail ->
                    hashMapOf(
                        "namaObat" to detail.namaObat,
                        "hargaSatuan" to detail.hargaSatuan,
                        "qty" to detail.qty,
                        "subtotal" to detail.subtotal
                    )
                }
            )
            
            firestore.collection("transaksi").document(transaksiBaru.idTransaksi.replace("#", ""))
                .set(trxMap)

            // Update stok obat di Room database
            val obatCounts = KeranjangManager.daftarKeranjang.groupBy { it.id }
            for ((id, list) in obatCounts) {
                val obat = list[0]
                val newStok = obat.stok - list.size
                obatViewModel.update(obat.copy(stok = if (newStok < 0) 0 else newStok))
            }

            KeranjangManager.daftarKeranjang.clear()
            KeranjangManager.saveCart(this)

            val intent = Intent(this, com.example.pharmatic.DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}

package com.example.pharmatic.data

import com.example.pharmatic.Kasir
import com.example.pharmatic.Suplier
import com.example.pharmatic.Transaksi
import com.example.pharmatic.data.database.PharmaTicDatabase
import com.example.pharmatic.obat.Obat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DataManager {
    val daftarObatAdmin = mutableListOf<Obat>()
    val daftarKasir = mutableListOf<Kasir>()
    val daftarSuplier = mutableListOf<Suplier>()
    val daftarTransaksi = mutableListOf<Transaksi>()

    val initialObat = listOf(
        Obat(nama = "Paracetamol 500mg", harga = 5000, stok = 50, jenis = "Tablet", deskripsi = "Pereda demam dan nyeri"),
        Obat(nama = "Sanmol", harga = 6000, stok = 45, jenis = "Tablet", deskripsi = "Obat penurun panas"),
        Obat(nama = "Panadol", harga = 7000, stok = 40, jenis = "Kaplet", deskripsi = "Obat sakit kepala"),
        Obat(nama = "Bodrex", harga = 4000, stok = 60, jenis = "Tablet", deskripsi = "Obat pusing kepala"),
        Obat(nama = "Bodrex Extra", harga = 5000, stok = 35, jenis = "Tablet", deskripsi = "Obat sakit kepala berat")
    )

    val initialKasir = listOf(
        Kasir(nama = "Ardy", username = "kasir", password = Kasir.hashPassword("1234"), fotoUri = null),
        Kasir(nama = "Budi", username = "budi_kasir", password = Kasir.hashPassword("1234"), fotoUri = null),
        Kasir(nama = "Siti Aminah", username = "siti22", password = Kasir.hashPassword("1234"), fotoUri = null)
    )

    val initialSuplier = listOf(
        Suplier(nama = "PT. Farmasi Jaya", alamat = "Jl. Raya Merdeka No. 10", telepon = "08123456789"),
        Suplier(nama = "CV. Sehat Abadi", alamat = "Jl. Melati No. 5, Bandung", telepon = "08219988776"),
        Suplier(nama = "Sumber Obat Mandiri", alamat = "Kawasan Industri Cikarang", telepon = "021-889977")
    )

    val initialTransaksi = listOf(
        Transaksi(idTransaksi = "#TRX-001", tanggal = "10 Feb 2025, 10:30", totalItem = 2, totalHarga = 15000, kasirNama = "Ardy"),
        Transaksi(idTransaksi = "#TRX-002", tanggal = "10 Feb 2025, 11:15", totalItem = 1, totalHarga = 25000, kasirNama = "Budi"),
        Transaksi(idTransaksi = "#TRX-003", tanggal = "11 Feb 2025, 09:00", totalItem = 4, totalHarga = 42500, kasirNama = "Siti Aminah")
    )

    suspend fun seedDatabase(database: PharmaTicDatabase) = withContext(Dispatchers.IO) {
        val obatDao = database.obatDao()
        val kasirDao = database.kasirDao()
        val suplierDao = database.suplierDao()
        val transaksiDao = database.transaksiDao()

        if (obatDao.getAllObatList().isEmpty()) {
            obatDao.insertAll(initialObat)
        }
        if (kasirDao.getAllKasirList().isEmpty()) {
            kasirDao.insertAll(initialKasir)
        }
        if (suplierDao.getAllSuplierList().isEmpty()) {
            suplierDao.insertAll(initialSuplier)
        }
        if (transaksiDao.getAllTransaksiList().isEmpty()) {
            transaksiDao.insertAll(initialTransaksi)
        }
    }
}

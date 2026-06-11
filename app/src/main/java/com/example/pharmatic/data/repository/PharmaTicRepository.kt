package com.example.pharmatic.data.repository

import androidx.lifecycle.LiveData
import com.example.pharmatic.Kasir
import com.example.pharmatic.Suplier
import com.example.pharmatic.Transaksi
import com.example.pharmatic.data.dao.KasirDao
import com.example.pharmatic.data.dao.ObatDao
import com.example.pharmatic.data.dao.SuplierDao
import com.example.pharmatic.data.dao.TransaksiDao
import com.example.pharmatic.data.dao.TransaksiDetailDao
import com.example.pharmatic.obat.Obat
import com.example.pharmatic.TransaksiDetail

class PharmaTicRepository(
    private val obatDao: ObatDao,
    private val kasirDao: KasirDao,
    private val suplierDao: SuplierDao,
    private val transaksiDao: TransaksiDao,
    private val transaksiDetailDao: TransaksiDetailDao
) {
    val allObat: LiveData<List<Obat>> = obatDao.getAllObat()
    val allKasir: LiveData<List<Kasir>> = kasirDao.getAllKasir()
    val allSuplier: LiveData<List<Suplier>> = suplierDao.getAllSuplier()
    val allTransaksi: LiveData<List<Transaksi>> = transaksiDao.getAllTransaksi()

    // Obat
    suspend fun insertObat(obat: Obat): Long = obatDao.insert(obat)
    suspend fun updateObat(obat: Obat) = obatDao.update(obat)
    suspend fun deleteObat(obat: Obat) = obatDao.delete(obat)
    suspend fun getObatStokRendah(): List<Obat> = obatDao.getObatStokRendah()
    suspend fun getAllObatList(): List<Obat> = obatDao.getAllObatList()

    // Kasir
    suspend fun insertKasir(kasir: Kasir): Long = kasirDao.insert(kasir)
    suspend fun updateKasir(kasir: Kasir) = kasirDao.update(kasir)
    suspend fun deleteKasir(kasir: Kasir) = kasirDao.delete(kasir)
    suspend fun verifyLogin(user: String, pass: String): Kasir? = kasirDao.verifyLogin(user, pass)
    suspend fun getAllKasirList(): List<Kasir> = kasirDao.getAllKasirList()

    // Suplier
    suspend fun insertSuplier(suplier: Suplier): Long = suplierDao.insert(suplier)
    suspend fun updateSuplier(suplier: Suplier) = suplierDao.update(suplier)
    suspend fun deleteSuplier(suplier: Suplier) = suplierDao.delete(suplier)
    suspend fun getAllSuplierList(): List<Suplier> = suplierDao.getAllSuplierList()

    // Transaksi
    suspend fun insertTransaksi(transaksi: Transaksi): Long = transaksiDao.insert(transaksi)
    suspend fun updateTransaksi(transaksi: Transaksi) = transaksiDao.update(transaksi)
    suspend fun deleteTransaksi(transaksi: Transaksi) = transaksiDao.delete(transaksi)
    suspend fun getAllTransaksiList(): List<Transaksi> = transaksiDao.getAllTransaksiList()

    // TransaksiDetail
    suspend fun insertTransaksiDetails(details: List<TransaksiDetail>) = transaksiDetailDao.insertAll(details)
    fun getDetailsByTransaksi(transaksiId: String): LiveData<List<TransaksiDetail>> = transaksiDetailDao.getDetailsByTransaksi(transaksiId)
}

package com.example.pharmatic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pharmatic.PharmaTicApp
import com.example.pharmatic.Transaksi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.example.pharmatic.TransaksiDetail

class TransaksiViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as PharmaTicApp).repository
    val allTransaksi: LiveData<List<Transaksi>> = repository.allTransaksi

    fun insert(transaksi: Transaksi) = viewModelScope.launch {
        repository.insertTransaksi(transaksi)
    }

    fun insertWithDetails(transaksi: Transaksi, details: List<TransaksiDetail>) = viewModelScope.launch {
        repository.insertTransaksi(transaksi)
        repository.insertTransaksiDetails(details)
    }

    fun update(transaksi: Transaksi) = viewModelScope.launch {
        repository.updateTransaksi(transaksi)
    }

    fun delete(transaksi: Transaksi) = viewModelScope.launch {
        repository.deleteTransaksi(transaksi)
    }

    fun getDetailsByTransaksi(transaksiId: String): LiveData<List<TransaksiDetail>> {
        return repository.getDetailsByTransaksi(transaksiId)
    }

    // Hitung total pendapatan async di background thread (IO)
    fun hitungTotalPendapatan(onResult: (String) -> Unit) = viewModelScope.launch {
        val total = withContext(Dispatchers.IO) {
            val list = repository.getAllTransaksiList()
            var sum = 0
            for (trx in list) {
                sum += trx.totalHarga
            }
            sum
        }
        val formatted = "Rp %,d".format(total).replace(',', '.')
        onResult(formatted)
    }
}

package com.example.pharmatic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pharmatic.Kasir
import com.example.pharmatic.PharmaTicApp
import kotlinx.coroutines.launch

class KasirViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as PharmaTicApp).repository
    val allKasir: LiveData<List<Kasir>> = repository.allKasir

    fun insert(kasir: Kasir) = viewModelScope.launch {
        repository.insertKasir(kasir)
    }

    fun update(kasir: Kasir) = viewModelScope.launch {
        repository.updateKasir(kasir)
    }

    fun delete(kasir: Kasir) = viewModelScope.launch {
        repository.deleteKasir(kasir)
    }

    fun verifyLogin(user: String, pass: String, onResult: (Kasir?) -> Unit) = viewModelScope.launch {
        val result = repository.verifyLogin(user, pass)
        onResult(result)
    }
}

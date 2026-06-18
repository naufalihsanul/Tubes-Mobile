package com.example.pharmatic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pharmatic.PharmaTicApp
import com.example.pharmatic.model.Obat
import kotlinx.coroutines.launch

class ObatViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as PharmaTicApp).repository
    val allObat: LiveData<List<Obat>> = repository.allObat

    fun insert(obat: Obat) = viewModelScope.launch {
        repository.insertObat(obat)
    }

    fun update(obat: Obat) = viewModelScope.launch {
        repository.updateObat(obat)
    }

    fun delete(obat: Obat) = viewModelScope.launch {
        repository.deleteObat(obat)
    }
}

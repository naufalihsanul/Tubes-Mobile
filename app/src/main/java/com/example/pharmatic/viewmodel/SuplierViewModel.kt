package com.example.pharmatic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pharmatic.PharmaTicApp
import com.example.pharmatic.model.Suplier
import kotlinx.coroutines.launch

class SuplierViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as PharmaTicApp).repository
    val allSuplier: LiveData<List<Suplier>> = repository.allSuplier

    fun insert(suplier: Suplier) = viewModelScope.launch {
        repository.insertSuplier(suplier)
    }

    fun update(suplier: Suplier) = viewModelScope.launch {
        repository.updateSuplier(suplier)
    }

    fun delete(suplier: Suplier) = viewModelScope.launch {
        repository.deleteSuplier(suplier)
    }
}

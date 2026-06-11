package com.example.pharmatic.keranjang

import android.content.Context
import com.example.pharmatic.obat.Obat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object KeranjangManager {
    val daftarKeranjang = mutableListOf<Obat>()

    fun saveCart(context: Context) {
        val sharedPref = context.getSharedPreferences("KeranjangPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val gson = Gson()
        val json = gson.toJson(daftarKeranjang)
        editor.putString("KERANJANG_DATA", json)
        editor.apply()
    }

    fun loadCart(context: Context) {
        val sharedPref = context.getSharedPreferences("KeranjangPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString("KERANJANG_DATA", null)
        
        daftarKeranjang.clear()
        if (json != null) {
            val type = object : TypeToken<List<Obat>>() {}.type
            val savedList: List<Obat> = gson.fromJson(json, type)
            daftarKeranjang.addAll(savedList)
        }
    }

    fun tambahObatByName(nama: String) {
        val obat = daftarKeranjang.find { it.nama == nama }
        if (obat != null) {
            daftarKeranjang.add(obat)
        }
    }

    fun kurangObat(nama: String) {
        val index = daftarKeranjang.indexOfFirst { it.nama == nama }
        if (index != -1) {
            daftarKeranjang.removeAt(index)
        }
    }

    fun hapusObat(nama: String) {
        daftarKeranjang.removeAll { it.nama == nama }
    }
}

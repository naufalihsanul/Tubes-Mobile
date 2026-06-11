package com.example.pharmatic.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("PharmaTicPrefs", Context.MODE_PRIVATE)

    fun simpanLogin(username: String, role: String) {
        prefs.edit().apply {
            putBoolean("IS_LOGGED_IN", true)
            putString("USERNAME", username)
            putString("ROLE", role)
            apply()
        }
    }

    fun cekLogin(): Boolean {
        return prefs.getBoolean("IS_LOGGED_IN", false)
    }

    fun getRole(): String? {
        return prefs.getString("ROLE", null)
    }

    fun getUsername(): String? {
        return prefs.getString("USERNAME", null)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}

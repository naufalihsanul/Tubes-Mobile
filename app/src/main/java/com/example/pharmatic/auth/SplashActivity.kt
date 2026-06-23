package com.example.pharmatic.auth

import com.example.pharmatic.DashboardActivity

import com.example.pharmatic.R

import com.example.pharmatic.admin.AdminDashboardActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmatic.data.SessionManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sessionManager = SessionManager(this)
        com.example.pharmatic.keranjang.KeranjangManager.loadCart(this)

        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.cekLogin()) {
                val role = sessionManager.getRole()
                if (role == "admin") {
                    startActivity(Intent(this, AdminDashboardActivity::class.java))
                } else {
                    startActivity(Intent(this, DashboardActivity::class.java))
                }
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 2000) // 2 detik
    }
}


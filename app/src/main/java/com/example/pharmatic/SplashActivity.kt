package com.example.pharmatic

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
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 2000) // 2 detik
    }
}

package com.example.pharmatic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmatic.data.SessionManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sessionManager = SessionManager(this)
        
        if (sessionManager.cekLogin()) {
            val role = sessionManager.getRole()
            if (role == "admin") {
                startActivity(Intent(this, AdminDashboardActivity::class.java))
            } else {
                startActivity(Intent(this, DashboardActivity::class.java))
            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        
        finish()
    }
}

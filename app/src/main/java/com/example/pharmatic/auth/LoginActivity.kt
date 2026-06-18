package com.example.pharmatic.auth

import com.example.pharmatic.DashboardActivity

import com.example.pharmatic.admin.AdminDashboardActivity

import com.example.pharmatic.R

import com.example.pharmatic.model.Kasir

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pharmatic.data.SessionManager
import com.example.pharmatic.viewmodel.KasirViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var kasirViewModel: KasirViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
        kasirViewModel = ViewModelProvider(this)[KasirViewModel::class.java]

        if (sessionManager.cekLogin()) {
            val role = sessionManager.getRole()
            if (role == "admin") {
                startActivity(Intent(this, AdminDashboardActivity::class.java))
            } else {
                startActivity(Intent(this, DashboardActivity::class.java))
            }
            finish()
            return
        }

        // Inisialisasi View
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (username == "admin" && password == "admin") {
                sessionManager.simpanLogin(username, "admin")
                val intent = Intent(this, AdminDashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Verifikasi dari Room Database
                kasirViewModel.verifyLogin(username, Kasir.hashPassword(password)) { kasir ->
                    if (kasir != null) {
                        sessionManager.simpanLogin(username, "kasir")
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

package com.example.pharmatic.api

import com.example.pharmatic.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // PENTING: Key Anda di local.properties adalah kunci PRODUCTION ("Mid-server-...")
    // Jadi URL-nya harus diarahkan ke URL Production Core API, bukan Sandbox!
    private const val BASE_URL = "https://api.midtrans.com/"

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            
            // Kunci di local.properties ("TWlkLXNlcnZlci...") itu SUDAH bentuk Base64.
            // Jangan di-encode lagi (jangan pakai Base64.encodeToString lagi).
            // Langsung saja tempelkan!
            val encodedKey = BuildConfig.MIDTRANS_API_KEY
            builder.header("Authorization", "Basic $encodedKey")
            
            return@Interceptor chain.proceed(builder.build())
        })
    }.build()

    val instance: MidtransApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MidtransApi::class.java)
    }
}

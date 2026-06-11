package com.example.pharmatic.api

import com.example.pharmatic.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Gunakan Sandbox untuk keperluan testing/Tugas Kuliah
    private const val BASE_URL = "https://api.sandbox.midtrans.com/"

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            
            // Ambil key dari BuildConfig
            var apiKey = BuildConfig.MIDTRANS_API_KEY
            // Jika user secara tidak sengaja memasukkan key yang sudah ter-encode Base64, kita decode dulu ke raw
            if (!apiKey.startsWith("SB-Mid-server") && !apiKey.startsWith("Mid-server") && apiKey.length > 30) {
                try {
                    val decodedBytes = android.util.Base64.decode(apiKey, android.util.Base64.DEFAULT)
                    apiKey = String(decodedBytes).replace(":", "")
                } catch (e: Exception) {
                    // Ignore
                }
            }
            
            // Midtrans mensyaratkan format Basic Base64(ServerKey + ":")
            val rawKey = "$apiKey:"
            val encodedKey = android.util.Base64.encodeToString(rawKey.toByteArray(), android.util.Base64.NO_WRAP)
            
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

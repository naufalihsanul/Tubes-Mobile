package com.example.pharmatic.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface MidtransApi {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("v2/charge")
    fun createTransaction(
        @Body request: MidtransRequest
    ): Call<MidtransResponse>
}

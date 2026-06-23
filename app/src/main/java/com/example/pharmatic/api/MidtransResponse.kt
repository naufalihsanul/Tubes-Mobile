package com.example.pharmatic.api

import com.google.gson.annotations.SerializedName

data class MidtransResponse(
    @SerializedName("token")
    val token: String?,
    @SerializedName("redirect_url")
    val redirectUrl: String?
)

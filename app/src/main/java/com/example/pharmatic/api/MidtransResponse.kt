package com.example.pharmatic.api

import com.google.gson.annotations.SerializedName

data class MidtransResponse(
    @SerializedName("status_code")
    val statusCode: String,
    @SerializedName("status_message")
    val statusMessage: String?,
    @SerializedName("transaction_id")
    val transactionId: String?,
    @SerializedName("order_id")
    val orderId: String?,
    @SerializedName("gross_amount")
    val grossAmount: String?,
    @SerializedName("payment_type")
    val paymentType: String?,
    @SerializedName("transaction_time")
    val transactionTime: String?,
    @SerializedName("transaction_status")
    val transactionStatus: String?,
    @SerializedName("actions")
    val actions: List<ActionItem>?
)

data class ActionItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("method")
    val method: String,
    @SerializedName("url")
    val url: String
)

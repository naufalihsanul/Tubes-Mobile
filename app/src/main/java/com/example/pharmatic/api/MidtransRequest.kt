package com.example.pharmatic.api

import com.google.gson.annotations.SerializedName

data class MidtransRequest(
    @SerializedName("payment_type")
    val paymentType: String = "qris",
    @SerializedName("transaction_details")
    val transactionDetails: TransactionDetails
)

data class TransactionDetails(
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("gross_amount")
    val grossAmount: Int
)

package com.bidwinko.model.ResponseModels

data class PaymentResponse(
    val message: String,
    val status: Int,
    val url: String
)
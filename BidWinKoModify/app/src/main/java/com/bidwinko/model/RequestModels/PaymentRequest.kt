package com.bidwinko.model.RequestModels

data class PaymentRequest(
    val userId: String,
    val securityToken: String,
    val versionName: String,
    val versionCode: String,
    val planId: String
)

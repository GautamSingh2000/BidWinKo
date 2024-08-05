package com.bidwinko.model.RequestModels

data class ProductDetailRequest(
    val bidId : String,
    val userId: String,
    val securityToken: String,
    val versionName: String,
    val versionCode: String
)

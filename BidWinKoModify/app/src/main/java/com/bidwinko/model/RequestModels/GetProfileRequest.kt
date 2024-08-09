package com.bidwinko.model.RequestModels

data class GetProfileRequest(
    val email: String? = "",
    val name: String? = "",
    val phone: String? = "",
    val address: String? = "",
    val method: String,
    val userId: String,
    val securityToken: String,
    val versionName: String,
    val versionCode: String
)

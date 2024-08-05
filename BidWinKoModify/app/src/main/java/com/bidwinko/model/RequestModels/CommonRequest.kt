package com.bidwinko.model.RequestModels

data class CommonRequest(
    val userId: String,
    val securityToken: String,
    val versionName: String,
    val versionCode: String
)
package com.bidwinko.model.RequestModels

import com.google.gson.annotations.SerializedName

data class AppOpenRequest(
    val userId: String,
    val securityToken: String,
    val versionName: String,
    val versionCode: String
)

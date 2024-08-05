package com.bidwinko.model.RequestModels

data class PlaceBidRequest(
    val bidId :String,
    val bids : ArrayList<String>,
    val userId: String,
    val securityToken: String,
    val versionName: String,
    val versionCode: String
)

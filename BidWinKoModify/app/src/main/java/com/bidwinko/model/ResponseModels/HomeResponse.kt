package com.bidwinko.model.ResponseModels

data class HomeResponse(
    val appBanners: List<AppBanner>,
    val message: String,
    val status: Int,
)


data class AppBanner(
    val actionUrl: String,
    val id: Int,
    val image: String
)
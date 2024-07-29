package com.bidwinko.model.ResponseModels

data class ProductDetailResponse(
    val productName :String,
    val productId :String,
    val productPrice :String,
    val productKeyFeature :String,
    val productEndTime : String,// in time stemp
)

package com.bidwinko.model.ResponseModels

data class ProductDetailResponse(
    val bidDetails: BidDetails,
    val message: String,
    val status: Int,
    val totalBids : String
)

data class BidDetails(
    val bidder: List<String>,
    val productEndTime: Int,
    val productStartTime : Int,
    val productId: Int,
    val productImage: List<String>,
    val productKeyFeature: String,
    val productName: String,
    val productPrice: String,
    val totalBid: Int
)
package com.bidwinko.model.ResponseModels

data class ClosedBidWinnerResponse(
    val status: Int,
    val message: String,
    val productDetail: ProdustDetail,
    val winnersList : List<ClosedWinnerList>
    )

data class ClosedWinnerList(
    val position : String,
    val name : String,
    val image : String,
    val prize : String,
)

data class ProdustDetail(
    val productImage: List<String>,
    val productKeyFeature: String,
    val productName: String,
    val productPrice: String,
    val productEndTime: String,
)

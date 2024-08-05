package com.bidwinko.model.ResponseModels

data class myBidsResponse(
    val bidImage: String,
    val bidTitle: String,
    val bids: List<Bid>,
    val endDate: String,
    val message: String,
    val startDate: String,
    val status: Int
) {
    data class Bid(
        val bidNo: String,
        val status: String
    )
}
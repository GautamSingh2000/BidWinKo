package com.bidwinko.model.ResponseModels

data class UserProductBidResponse(
    val status : Int,
    val message :String,
    val userBids : ArrayList<String>,
    val cancelledBids : ArrayList<String>
)

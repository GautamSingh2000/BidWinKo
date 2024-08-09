package com.bidwinko.model.ResponseModels

data class GetProfileResponse(
    val address: String,
    val message: String,
    val mobileNumber: String,
    val status: Int,
    val userEmail: String,
    val userImage: String,
    val userName: String
)
package com.bidwinko.model.ResponseModels
data class winners_Response_Model(
    val status: Int,
    val message: String,
    val winner_details: List<winnerDetail>,
)

data class winnerDetail(
    val user_Image: String,
    val user_Name: String,
    val winnning_Bid: String,
    val location: String,
    val product_Image: String,
    val product_Name: String,
    val price: String
)

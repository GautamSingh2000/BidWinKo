package com.bidwinko.model.ResponseModels


data class HomeListDataResponse(
val offers: List<HomeList>,
val message: String,
val status: Int,
)
data class HomeList(
    val endDate: Long,
    val startDate: Long,
    val id: Int,
    val offerImage: String,
    val offerName: String,
    val offerPrice: String
)

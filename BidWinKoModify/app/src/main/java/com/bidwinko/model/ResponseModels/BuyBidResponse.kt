package com.bidwinko.model.ResponseModels

data class BuyBidResponse(
    val bidPlans: List<BidPlan>,
    val message: String,
    val status: Int
)
data class BidPlan(
    val categoryId: Int,
    val categoryTitle: String,
    val plans: List<Plan>
)
data class Plan(
    val expiresIn: String,
    val noOfBids: String,
    val offPercentage: String,
    val planId: Int,
    val planPrice: Any
)
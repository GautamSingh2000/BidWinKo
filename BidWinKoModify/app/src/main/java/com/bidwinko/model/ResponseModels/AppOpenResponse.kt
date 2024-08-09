package com.bidwinko.model.ResponseModels

import com.google.gson.annotations.SerializedName

data class AppOpenResponse(
    @SerializedName("message") val message: String,
    @SerializedName("totalBids") val bids: String,
    @SerializedName("status") val status: Int,
    @SerializedName("forceUpdate") val forceUpdate: Boolean,
    @SerializedName("appUrl") val appUrl: String,
)
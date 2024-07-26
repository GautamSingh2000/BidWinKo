package com.bidwinko.model.ResponseModels

data class HomeResponse(
    val appBanners: List<AppBanner>,
    val completedOffers: List<ListData>,
    val liveOffers: List<ListData>,
    val message: String,
    val status: Int,
    val upcomingOffers: List<ListData>
)

data class ListData(
    val endDate: Long,
    val startDate: Long,
    val id: Int,
    val offerImage: String,
    val offerName: String,
    val offerPrice: String
)

data class AppBanner(
    val actionUrl: String,
    val id: Int,
    val image: String
)
//
//data class CompletedOffer(
//    val endDate: String,
//    val id: Int,
//    val offerImage: String,
//    val offerName: String,
//    val offerPrice: String
//)
//
//data class LiveOffer(
//    val endDate: String,
//    val id: Int,
//    val offerImage: String,
//    val offerName: String,
//    val offerPrice: String
//)
//
//data class UpcomingOffer(
//    val endDate: String,
//    val id: Int,
//    val offerImage: String,
//    val offerName: String,
//    val offerPrice: String
//)
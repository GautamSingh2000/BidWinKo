package com.bidwinko.API

import com.bidwinko.model.BidPurchaseModel
import com.bidwinko.model.BuyBid
import com.bidwinko.model.GenerateTokenModel
import com.bidwinko.model.InviteFriendModel
import com.bidwinko.model.ProductsDetailsModel
import com.bidwinko.model.ResponseModels.AppOpenResponse
import com.bidwinko.model.RequestModels.ProductDetailRequest
import com.bidwinko.model.RequestModels.AppOpenRequest
import com.bidwinko.model.RequestModels.ClosedBidWinnerRequest
import com.bidwinko.model.RequestModels.CommonRequest
import com.bidwinko.model.RequestModels.GetProfileRequest
import com.bidwinko.model.RequestModels.PaymentRequest
import com.bidwinko.model.RequestModels.PlaceBidRequest
import com.bidwinko.model.ResponseModels.HomeResponse
import com.bidwinko.model.ResponseModels.ProductDetailResponse
import com.bidwinko.model.RequestModels.UserSignUpRequest
import com.bidwinko.model.ResponseModels.BuyBidResponse
import com.bidwinko.model.ResponseModels.ClosedBidWinnerResponse
import com.bidwinko.model.ResponseModels.GetProfileResponse
import com.bidwinko.model.ResponseModels.HomeListDataResponse
import com.bidwinko.model.ResponseModels.PaymentResponse
import com.bidwinko.model.ResponseModels.PlaceBidResponse
import com.bidwinko.model.ResponseModels.TransactionHistoryResponse
import com.bidwinko.model.ResponseModels.winners_Response_Model
import com.bidwinko.model.ShowUserBidsModel
import com.bidwinko.model.SingleBidSubmitModel
import com.bidwinko.model.ResponseModels.USerSignUpResponse
import com.bidwinko.model.ResponseModels.UserProductBidResponse
import com.bidwinko.model.ResponseModels.myBidsResponse
import com.bidwinko.model.UserDetailsModel
import com.bidwinko.model.UserTransactionModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface APIService {
    @POST("home")
    fun GetHomeData(@Body homeRequest: CommonRequest): Call<HomeResponse>
    @POST("transactionHistory")
    fun GetTransactionHistory(@Body homeRequest: CommonRequest): Call<TransactionHistoryResponse>
    @POST("liveBids")
    fun GetliveBidsList(@Body homeRequest: CommonRequest): Call<HomeListDataResponse>
    @POST("upcomingBids")
    fun GetupcomingBidsList(@Body homeRequest: CommonRequest): Call<HomeListDataResponse>
    @POST("completedBids")
    fun GetcompletedBidsList(@Body homeRequest: CommonRequest): Call<HomeListDataResponse>
    @POST("profile")
    fun GetProfile(@Body getProfileRequest: GetProfileRequest): Call<GetProfileResponse>

    @POST("bidPastWinners")
    fun GetWinnerList(@Body commonRequest: CommonRequest): Call<winners_Response_Model>

    @POST("closedBidWinner")
    fun GetBidWinner(@Body closedBidWinnerRequest: ClosedBidWinnerRequest): Call<ClosedBidWinnerResponse>


    @POST("bidDetails")
    fun GetProductDetail(@Body productDetailRequest: ProductDetailRequest): Call<ProductDetailResponse>

    @POST("myBids")
    fun GetMyBids(@Body productDetailRequest: ProductDetailRequest): Call<myBidsResponse>

    @POST("userSignup")
    fun UserSignUp(@Body userSignUpRequest: UserSignUpRequest): Call<USerSignUpResponse>

    @POST("appOpen")
    fun AppOpen(@Body appOpenRequest: AppOpenRequest): Call<AppOpenResponse>
    @POST("placeBid")
    fun PlaceBid(@Body placeBidRequest: PlaceBidRequest): Call<PlaceBidResponse>

    @POST("userProductBids")
    fun GetUserProductBid(@Body productDetailRequest: ProductDetailRequest): Call<UserProductBidResponse>
    @POST("buyBids")
    fun GetBidPacakage(@Body commonRequest: CommonRequest): Call<BuyBidResponse>
    @POST("payment")
    fun GetPayment(@Body paymentRequest: PaymentRequest): Call<PaymentResponse>
}
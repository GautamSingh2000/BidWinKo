package com.bidwinko.API

import com.bidwinko.model.BidPurchaseModel
import com.bidwinko.model.BuyBid
import com.bidwinko.model.GenerateTokenModel
import com.bidwinko.model.InviteFriendModel
import com.bidwinko.model.ProductsDetailsModel
import com.bidwinko.model.ResponseModels.AppOpenResponse
import com.bidwinko.model.RequestModels.ProductDetailRequest
import com.bidwinko.model.RequestModels.AppOpenRequest
import com.bidwinko.model.RequestModels.CommonRequest
import com.bidwinko.model.RequestModels.PlaceBidRequest
import com.bidwinko.model.ResponseModels.HomeResponse
import com.bidwinko.model.ResponseModels.ProductDetailResponse
import com.bidwinko.model.RequestModels.UserSignUpRequest
import com.bidwinko.model.ResponseModels.BuyBidResponse
import com.bidwinko.model.ResponseModels.PlaceBidResponse
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

    @POST("bidPastWinners")
    fun GetWinnerList(): Call<winners_Response_Model>


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


    @FormUrlEncoded
    @POST("buyBidPage")
    fun getbuyBids(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("userFrom") userFrom: String?,
    ): Call<BuyBid?>?

    @FormUrlEncoded
    @POST("bidUserProfile")
    fun getUserDetails(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("userFrom") userFrom: String?,
    ): Call<UserDetailsModel?>?

    @FormUrlEncoded
    @POST("bidUserProfile")
    fun updateuserDetails(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("username") username: String?,
        @Field("useemail") useremail: String?,
        @Field("usermobile") usermobile: String?,
        @Field("shipingaddress") shpingaddress: String?,
        @Field("actiontype") actiontype: String?,
        @Field("userFrom") userFrom: String?,
    ): Call<UserDetailsModel?>?

    @FormUrlEncoded
    @POST("showUserBid")
    fun showUserBids(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("bidofferId") bidofferId: String?,
        @Field("userFrom") userFrom: String?,
    ): Call<ShowUserBidsModel?>?

    @FormUrlEncoded
    @POST("bidInviteData")
    fun inviteDataFriend(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("userFrom") userFrom: String?,
    ): Call<InviteFriendModel?>?

    @FormUrlEncoded
    @POST("placeOneBid")
    fun submitSingleBid(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("bidValue") bidValue: String?,
        @Field("bidofferId") bidofferId: String?,
        @Field("userFrom") userFrom: String?,
    ): Call<SingleBidSubmitModel?>?

    @FormUrlEncoded
    @POST("placeRangeBid")
    fun submitBidfromTo(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("bidValueFm") bidValueFm: String?,
        @Field("bidValueTo") bidValueTo: String?,
        @Field("bidofferId") bidofferId: String?,
        @Field("userFrom") userFrom: String?,
    ): Call<SingleBidSubmitModel?>?

    @POST("bidUserTransactions")
    @FormUrlEncoded
    fun getUserTransactions(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("userFrom") userFrom: String?,
    ): Call<UserTransactionModel?>?

    @POST("bidPurchase")
    @FormUrlEncoded
    fun bidPurchase(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("buybidId") buybidId: String?,
        @Field("userFrom") userFrom: String?,
        @Field("vendor") vendor: String?,
        @Field("orderId") orderId: String?,
        @Field("productId") productId: String?,
        @Field("packageName") packageName: String?,
        @Field("developerPayload") developerPayload: String?,
        @Field("purchaseTime") purchaseTime: String?,
        @Field("purchaseState") purchaseState: String?,
        @Field("purchaseToken") purchaseToken: String?,
    ): Call<BidPurchaseModel?>?

    @GET("generate-cftoken.json")
    fun generateToken(@QueryMap options: Map<String?, String?>?): Call<GenerateTokenModel?>?
}
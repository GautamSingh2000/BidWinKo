package com.bidwinko.API

import android.net.Uri
import com.bidwinko.model.AppOpenModel
import com.bidwinko.model.BidPurchaseModel
import com.bidwinko.model.BuyBid
import com.bidwinko.model.GenerateTokenModel
import com.bidwinko.model.InviteFriendModel
import com.bidwinko.model.ProductsDetailsModel
import com.bidwinko.model.ResponseModels.HomeResponse
import com.bidwinko.model.ResponseModels.winners_Response_Model
import com.bidwinko.model.ShowUserBidsModel
import com.bidwinko.model.SignUpModel
import com.bidwinko.model.SingleBidSubmitModel
import com.bidwinko.model.UserDetailsModel
import com.bidwinko.model.UserTransactionModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface APIService {
    @POST("home")
    fun GetHomeData(): Call<HomeResponse>

    @FormUrlEncoded
    @POST("bidUserSignup")
    fun userSignUp(
        @Field("deviceType") deviceType: String?,
        @Field("deviceId") deviceId: String?,
        @Field("deviceName") deviceName: String?,
        @Field("socialType") socialType: String?,
        @Field("socialId") socialId: String?,
        @Field("socialEmail") socialEmail: String?,
        @Field("socialName") socialName: String?,
        @Field("socialImgurl") socialImgurl: Uri?,
        @Field("advertisingId") advertisingId: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("fcmToken") token: String?,
        @Field("utmSource") utmSource: String?,
        @Field("utmMedium") utmMedium: String?,
        @Field("utmTerm") utmTerm: String?,
        @Field("utmContent") utmContent: String?,
        @Field("utmCampaign") utmCampaign: String?,
        @Field("userFrom") userFrom: String?,
        @Field("socialToken") socialToken: String?
    ): Call<SignUpModel?>?

    @FormUrlEncoded
    @POST("bidAppOpen")
    fun appOpen(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("userFrom") userFrom: String?
    ): Call<AppOpenModel?>?

    //    @FormUrlEncoded
    //    @POST("bidOfferList")
    //    Call<ProductsListModel> mybidOffer(@Field("userId") int userId,
    //                                       @Field("securityToken") String securityToken,
    //                                       @Field("versionName") String versionName,
    //                                       @Field("versionCode") int versionCode,
    //                                       @Field("userFrom") String userFrom);

    @FormUrlEncoded///////////////////////////////
    @POST("bidofferDetails")
    fun getProductDetails(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("bidofferId") bidofferId: String?,
        @Field("userFrom") userFrom: String?
    ): Call<ProductsDetailsModel?>?

    @POST("bidPastWinners")
    fun GetWinnerList () : Call<winners_Response_Model>

    @FormUrlEncoded
    @POST("buyBidPage")
    fun getbuyBids(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("userFrom") userFrom: String?
    ): Call<BuyBid?>?

    @FormUrlEncoded
    @POST("bidUserProfile")
    fun getUserDetails(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("userFrom") userFrom: String?
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
        @Field("userFrom") userFrom: String?
    ): Call<UserDetailsModel?>?

    @FormUrlEncoded
    @POST("showUserBid")
    fun showUserBids(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("bidofferId") bidofferId: String?,
        @Field("userFrom") userFrom: String?
    ): Call<ShowUserBidsModel?>?

    @FormUrlEncoded
    @POST("bidInviteData")
    fun inviteDataFriend(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("userFrom") userFrom: String?
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
        @Field("userFrom") userFrom: String?
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
        @Field("userFrom") userFrom: String?
    ): Call<SingleBidSubmitModel?>?

    @POST("bidUserTransactions")
    @FormUrlEncoded
    fun getUserTransactions(
        @Field("userId") userId: Int,
        @Field("securityToken") securityToken: String?,
        @Field("versionName") versionName: String?,
        @Field("versionCode") versionCode: Int,
        @Field("userFrom") userFrom: String?
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
        @Field("purchaseToken") purchaseToken: String?
    ): Call<BidPurchaseModel?>?

    @GET("generate-cftoken.json")
    fun generateToken(@QueryMap options: Map<String?, String?>?): Call<GenerateTokenModel?>?
}
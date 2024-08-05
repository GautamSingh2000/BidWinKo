package com.bidwinko.model.RequestModels

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class UserSignUpRequest(
    @SerializedName("deviceType") val deviceType: String,
    @SerializedName("deviceId") val deviceId: String,
    @SerializedName("deviceName") val deviceName: String,
    @SerializedName("socialType") val socialType: String,
    @SerializedName("socialId") val socialId: String,
    @SerializedName("socialEmail") val socialEmail: String,
    @SerializedName("socialName") val socialName: String,
    @SerializedName("socialImgurl") val socialImgurl: String,
    @SerializedName("advertisingId") val advertisingId: String,
    @SerializedName("versionName") val versionName: String,
    @SerializedName("versionCode") val versionCode: String,
    @SerializedName("utmSource") val utmSource: String,
    @SerializedName("utmMedium") val utmMedium: String,
    @SerializedName("utmTerm") val utmTerm: String,
    @SerializedName("utmContent") val utmContent: String,
    @SerializedName("utmCampaign") val utmCampaign: String,
    @SerializedName("socialToken") val socialToken: String,
    @SerializedName("referalUrl") val referalUrl: String
)

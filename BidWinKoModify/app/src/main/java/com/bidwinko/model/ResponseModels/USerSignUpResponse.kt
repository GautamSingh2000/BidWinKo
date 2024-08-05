package com.bidwinko.model.ResponseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class USerSignUpResponse {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("userId")
    @Expose
    var userId = 0

    @SerializedName("securityToken")
    @Expose
    var securityToken: String? = null
}
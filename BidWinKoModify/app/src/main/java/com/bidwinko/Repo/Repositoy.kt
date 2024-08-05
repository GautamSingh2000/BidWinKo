package com.bidwinko.Repo

import android.content.Context
import com.bidwinko.API.APIService
import com.bidwinko.API.Retrofit
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
import com.bidwinko.model.ResponseModels.USerSignUpResponse
import com.bidwinko.model.ResponseModels.UserProductBidResponse
import com.bidwinko.model.ResponseModels.myBidsResponse
import retrofit2.Call

class Repositoy(val context: Context) {
    private val Retrofit_object = Retrofit.getInstance().create(APIService::class.java)

    fun GetHomeData(homeRequest: CommonRequest): Call<HomeResponse> {
        return Retrofit_object.GetHomeData(homeRequest = homeRequest)
    }

    fun GetWinnersList(): Call<winners_Response_Model> {
        return Retrofit_object.GetWinnerList()
    }

    fun GetProductDetail(productDetailRequest: ProductDetailRequest): Call<ProductDetailResponse> {
        return Retrofit_object.GetProductDetail(productDetailRequest)
    }

    fun GEtMyBids(productDetailRequest: ProductDetailRequest): Call<myBidsResponse> {
        return Retrofit_object.GetMyBids(productDetailRequest)
    }

        fun GetUserProductBid(productDetailRequest: ProductDetailRequest): Call<UserProductBidResponse> {
            return Retrofit_object.GetUserProductBid(productDetailRequest)
        }


        fun Signup(userSignUpRequest: UserSignUpRequest): Call<USerSignUpResponse> {
            return Retrofit_object.UserSignUp(userSignUpRequest)
        }

        fun AppOpen(appOpenRequest: AppOpenRequest): Call<AppOpenResponse> {
            return Retrofit_object.AppOpen(appOpenRequest = appOpenRequest)
        }

        fun PlaceBid(PlaceBidRequest: PlaceBidRequest): Call<PlaceBidResponse> {
            return Retrofit_object.PlaceBid(placeBidRequest = PlaceBidRequest)
        }
        fun GetBidsPacakage(commonRequest: CommonRequest): Call<BuyBidResponse> {
            return Retrofit_object.GetBidPacakage(commonRequest)
        }

}
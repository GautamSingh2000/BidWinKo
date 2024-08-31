package com.bidwinko.Repo

import ApiClient
import android.content.Context
import com.bidwinko.API.APIService
import com.bidwinko.model.RequestModels.AppOpenRequest
import com.bidwinko.model.RequestModels.ClosedBidWinnerRequest
import com.bidwinko.model.RequestModels.CommonRequest
import com.bidwinko.model.RequestModels.GetProfileRequest
import com.bidwinko.model.RequestModels.PaymentRequest
import com.bidwinko.model.RequestModels.PlaceBidRequest
import com.bidwinko.model.RequestModels.ProductDetailRequest
import com.bidwinko.model.RequestModels.UserSignUpRequest
import com.bidwinko.model.ResponseModels.AppOpenResponse
import com.bidwinko.model.ResponseModels.BuyBidResponse
import com.bidwinko.model.ResponseModels.ClosedBidWinnerResponse
import com.bidwinko.model.ResponseModels.GetProfileResponse
import com.bidwinko.model.ResponseModels.HomeListDataResponse
import com.bidwinko.model.ResponseModels.HomeResponse
import com.bidwinko.model.ResponseModels.PaymentResponse
import com.bidwinko.model.ResponseModels.PlaceBidResponse
import com.bidwinko.model.ResponseModels.ProductDetailResponse
import com.bidwinko.model.ResponseModels.TransactionHistoryResponse
import com.bidwinko.model.ResponseModels.USerSignUpResponse
import com.bidwinko.model.ResponseModels.UserProductBidResponse
import com.bidwinko.model.ResponseModels.myBidsResponse
import com.bidwinko.model.ResponseModels.winners_Response_Model
import retrofit2.Call

class Repositoy(val context: Context) {
    private val Retrofit_object = ApiClient.getInstance().create(APIService::class.java)

    fun GetHomeData(homeRequest: CommonRequest): Call<HomeResponse> {
        return Retrofit_object.GetHomeData(homeRequest = homeRequest)
    }
  fun GetTransactionHistory(homeRequest: CommonRequest): Call<TransactionHistoryResponse> {
        return Retrofit_object.GetTransactionHistory(homeRequest = homeRequest)
    }

    fun GetliveBidsList(homeRequest: CommonRequest): Call<HomeListDataResponse> {
        return Retrofit_object.GetliveBidsList(homeRequest = homeRequest)
    }
    fun GetupcomingBidsList(homeRequest: CommonRequest): Call<HomeListDataResponse> {
        return Retrofit_object.GetupcomingBidsList(homeRequest = homeRequest)
    }
    fun GetcompletedBidsList(homeRequest: CommonRequest): Call<HomeListDataResponse> {
        return Retrofit_object.GetcompletedBidsList(homeRequest = homeRequest)
    }

    fun GetWinnersList(homeRequest: CommonRequest): Call<winners_Response_Model> {
        return Retrofit_object.GetWinnerList(homeRequest)
    }
    fun GEtProfile(getProfileRequest: GetProfileRequest): Call<GetProfileResponse> {
        return Retrofit_object.GetProfile(getProfileRequest = getProfileRequest)
    }
    fun GetClosedWinnersList(closedBidWinnerRequest: ClosedBidWinnerRequest): Call<ClosedBidWinnerResponse> {
        return Retrofit_object.GetBidWinner(closedBidWinnerRequest)
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
        fun GetPayment(commonRequest: PaymentRequest): Call<PaymentResponse> {
            return Retrofit_object.GetPayment(commonRequest)
        }

}
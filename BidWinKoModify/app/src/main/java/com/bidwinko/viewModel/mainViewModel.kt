package com.bidwinko.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bidwinko.Repo.Repositoy
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
import retrofit2.Callback
import retrofit2.Response

class mainViewModel(val context: Context) : ViewModel() {
    private var repo: Repositoy = Repositoy(context = context)

    private val tag = "ViewModel"
    private val ongoingCalls = mutableListOf<Call<*>>()

    private var HomeResponse = MutableLiveData<HomeResponse>()
    private var WinnerList = MutableLiveData<winners_Response_Model>()
    private var ProductDetail = MutableLiveData<ProductDetailResponse>()
    private var MyBids = MutableLiveData<myBidsResponse>()
    private var UserSignUp = MutableLiveData<USerSignUpResponse>()
    private var AppOpen = MutableLiveData<AppOpenResponse>()
    private var PlaceBid = MutableLiveData<PlaceBidResponse>()
    private var UserProductBid = MutableLiveData<UserProductBidResponse>()
    private var BuyBids = MutableLiveData<BuyBidResponse>()


    private fun <T> enqueueCall(call: Call<T>, liveData: MutableLiveData<T>) {
        ongoingCalls.add(call)
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    Log.e(tag, "in Enquecall ${response.body()}")
                    if (response.body() != null) {
                        liveData.value = response.body()
                    } else {
                        Log.e(tag,"data is null")
                    }
                }
                ongoingCalls.remove(call)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.e(tag, "in onFail of GetLeagueData ${t.message}")
            }
        })
        ongoingCalls.remove(call)
    }

    fun PlaceBid(placeBidRequest: PlaceBidRequest):MutableLiveData<PlaceBidResponse>
    {
        val call = repo.PlaceBid(placeBidRequest)
        enqueueCall(call,PlaceBid)
        return PlaceBid
    }

    fun GetHomeData(homeRequest: CommonRequest):MutableLiveData<HomeResponse>
    {
        val call = repo.GetHomeData(homeRequest)
        enqueueCall(call, HomeResponse)
        return HomeResponse
    }
    fun GetWinnerList():MutableLiveData<winners_Response_Model>
    {
        val call = repo.GetWinnersList()
        enqueueCall(call,WinnerList)
        return WinnerList
    }

 fun GetProductDetail(productDetailRequest: ProductDetailRequest):MutableLiveData<ProductDetailResponse>
    {
        val call = repo.GetProductDetail(productDetailRequest)
        enqueueCall(call,ProductDetail)
        return ProductDetail
    }
 fun GetMyBids(productDetailRequest: ProductDetailRequest):MutableLiveData<myBidsResponse>
    {
        val call = repo.GEtMyBids(productDetailRequest)
        enqueueCall(call,MyBids)
        return MyBids
    }
 fun GetUserProductBid(productDetailRequest: ProductDetailRequest):MutableLiveData<UserProductBidResponse>
    {
        val call = repo.GetUserProductBid(productDetailRequest)
        enqueueCall(call,UserProductBid)
        return UserProductBid
    }
 fun Signup(userSignUpRequest: UserSignUpRequest):MutableLiveData<USerSignUpResponse>
    {
        val call = repo.Signup(userSignUpRequest)
        enqueueCall(call,UserSignUp)
        return UserSignUp
    }
 fun appOpen(appOpenRequest: AppOpenRequest):MutableLiveData<AppOpenResponse>
    {
        val call = repo.AppOpen(appOpenRequest)
        enqueueCall(call,AppOpen)
        return AppOpen
    }
 fun GetBidsPacakage(commonRequest: CommonRequest):MutableLiveData<BuyBidResponse>
    {
        val call = repo.GetBidsPacakage(commonRequest)
        enqueueCall(call,BuyBids)
        return BuyBids
    }



}
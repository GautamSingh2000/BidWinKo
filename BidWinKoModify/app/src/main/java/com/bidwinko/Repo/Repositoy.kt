package com.bidwinko.Repo

import android.content.Context
import com.bidwinko.API.APIService
import com.bidwinko.API.Retrofit
import com.bidwinko.model.ResponseModels.HomeResponse
import com.bidwinko.model.ResponseModels.winners_Response_Model
import retrofit2.Call

class Repositoy(val context: Context) {
    private val Retrofit_object = Retrofit.getInstance().create(APIService::class.java)

    fun GetHomeData(): Call<HomeResponse> {
        return Retrofit_object.GetHomeData()
    }

    fun GetWinnersList(): Call<winners_Response_Model> {
        return Retrofit_object.GetWinnerList()
    }
}
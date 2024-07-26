package com.bidwinko.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bidwinko.Repo.Repositoy
import com.bidwinko.model.ResponseModels.HomeResponse
import com.bidwinko.model.ResponseModels.winners_Response_Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class mainViewModel(val context: Context) : ViewModel() {
    private var repo: Repositoy = Repositoy(context = context)

    private val tag = "ViewModel"
    private val ongoingCalls = mutableListOf<Call<*>>()

    private var HomeResponse = MutableLiveData<HomeResponse>()
    private var WinnerList = MutableLiveData<winners_Response_Model>()


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

    fun GetHomeData():MutableLiveData<HomeResponse>
    {
        val call = repo.GetHomeData()
        enqueueCall(call, HomeResponse)
        return HomeResponse
    }
    fun GetWinnerList():MutableLiveData<winners_Response_Model>
    {
        val call = repo.GetWinnersList()
        enqueueCall(call,WinnerList)
        return WinnerList
    }




}
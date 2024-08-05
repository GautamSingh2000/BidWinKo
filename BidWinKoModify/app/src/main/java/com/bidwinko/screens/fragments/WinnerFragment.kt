package com.bidwinko.screens.fragments

import android.animation.Animator
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.bidwinko.R
import com.bidwinko.adapter.WinnerListAdapter
import com.bidwinko.databinding.FragmentWinnerBinding
import com.bidwinko.API.APIService
import com.bidwinko.model.ResponseModels.winnerDetail
import com.bidwinko.model.ResponseModels.winners_Response_Model
import com.bidwinko.API.Retrofit
import com.bidwinko.viewModel.mainViewModel
import kotlinx.coroutines.MainScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WinnerFragment : Fragment() {
    private var winnerDetailArrayList = ArrayList<winnerDetail>()
    private var mAdapter: WinnerListAdapter? = null
    private var progressDialog: ProgressDialog? = null
    private var binding: FragmentWinnerBinding? = null
    private lateinit var viewModel: mainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWinnerBinding.inflate(inflater, container, false)
        val view = binding?.root
        viewModel = mainViewModel(requireContext())
        requireActivity().findViewById<TextView>(R.id.title).setText(R.string.winner)
        setupAnimations()
        fetchWinnerList()

        return view
    }

    private fun setupAnimations() {
        binding?.trophyAnimation1?.playAnimation()
        binding?.trophyAnimation2?.playAnimation()
        val anim3 = binding?.trophyAnimation4
        anim3?.playAnimation()
        binding?.trophyAnimation3?.playAnimation()
        binding?.trophyAnimation4?.playAnimation()
        anim3?.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                anim3.playAnimation()
                binding?.trophyAnimation1?.playAnimation()
                binding?.trophyAnimation3?.playAnimation()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    private fun fetchWinnerList() {
        if (activity?.isFinishing == false) {
            progressDialog = ProgressDialog(activity).apply {
                setMessage(getString(R.string.loadingwait))
                show()
                setCancelable(false)
            }
        }


        viewModel.GetWinnerList().observe(requireActivity()){
            dismissProgressDialog()
            if(it.status == 200)
            {
                winnerDetailArrayList =it.winner_details as ArrayList<winnerDetail>
                mAdapter = WinnerListAdapter(winnerDetailArrayList,requireContext())
                binding?.bidRecyclerView?.apply {
                    itemAnimator = DefaultItemAnimator()
                    adapter = mAdapter
                }
            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.systemmessage) + it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

//        val call = apiService.getWinnersList(
//            Constants.getSharedPreferenceInt(activity, "userId", 0),
//            Constants.getSharedPreferenceString(activity, "securitytoken", ""),
//            Constants.getSharedPreferenceString(activity, "versionName", ""),
//            Constants.getSharedPreferenceInt(activity, "versionCode", 0),
//            Constants.getSharedPreferenceString(activity, "userFrom", "")
//        )

//        call.enqueue(object : Callback<winners_Response_Model> {
//            override fun onResponse(
//                call: Call<winners_Response_Model>,
//                response: Response<winners_Response_Model>,
//            ) {
//                dismissProgressDialog()
//                if (response.isSuccessful && response.body()?.status == 200) {
//                    winnerDetailArrayList = response.body()!!.winner_details as ArrayList<winnerDetail>
//                    mAdapter = WinnerListAdapter(winnerDetailArrayList, activity!!)
//                    binding?.recyclerView?.apply {
//                        itemAnimator = DefaultItemAnimator()
//                        adapter = mAdapter
//                    }
//                } else {
//                    Toast.makeText(
//                        activity,
//                        getString(R.string.systemmessage) + response.body()?.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<winners_Response_Model>, t: Throwable) {
//                dismissProgressDialog()
//                Log.e("response", t.toString())
//                Toast.makeText(
//                    activity,
//                    getString(R.string.systemmessage) + t.message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
    }

    private fun dismissProgressDialog() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }

    override fun onDestroy() {
        dismissProgressDialog()
        super.onDestroy()
    }

    override fun onPause() {
        dismissProgressDialog()
        super.onPause()
    }

    companion object {
        @JvmStatic
        fun newInstance(): WinnerFragment = WinnerFragment()
    }
}

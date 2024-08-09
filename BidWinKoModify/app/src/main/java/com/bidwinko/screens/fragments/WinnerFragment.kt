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
import com.bidwinko.model.RequestModels.CommonRequest
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel
import kotlinx.coroutines.MainScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WinnerFragment : Fragment() {
    private var winnerDetailArrayList = ArrayList<winnerDetail>()
    private var mAdapter: WinnerListAdapter? = null
    private lateinit var binding: FragmentWinnerBinding
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
        val  request = CommonRequest(
            userId = SessionManager(requireContext()).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(requireContext()).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(requireContext()).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(requireContext()).GetValue(Constants.VERSION_CODE)
        )

        viewModel.GetWinnerList(request).observe(requireActivity()){
            binding.shimmer.startShimmer()
            binding.shimmer.visibility = View.GONE
            if(it.status == 200)
            {
                winnerDetailArrayList =it.winner_details as ArrayList<winnerDetail>
                if(!winnerDetailArrayList.isNullOrEmpty()) {
                    mAdapter = WinnerListAdapter(winnerDetailArrayList, requireContext())
                    binding?.bidRecyclerView?.apply {
                        itemAnimator = DefaultItemAnimator()
                        adapter = mAdapter
                    }
                }else{
                binding.noDataFound.root.visibility = View.VISIBLE
                binding.noDataFound.anim.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        binding.noDataFound.anim.playAnimation()
                    }

                    override fun onAnimationCancel(animation: Animator) {
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                    }

                })}
            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.systemmessage) + it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): WinnerFragment = WinnerFragment()
    }
}

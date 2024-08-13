package com.bidwinko.screens.fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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


private lateinit var const: FragmentActivity
class WinnerFragment : Fragment() {
    private var winnerDetailArrayList = ArrayList<winnerDetail>()
    private var mAdapter: WinnerListAdapter? = null
    private lateinit var binding: FragmentWinnerBinding
    private lateinit var viewModel: mainViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            const = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWinnerBinding.inflate(inflater, container, false)
        val view = binding?.root
        viewModel = mainViewModel(const)
        const.findViewById<TextView>(R.id.title).setText(R.string.winner)
        setupAnimations()
        fetchWinnerList()

        return view
    }

    private fun setupAnimations() {
        
        val one_fadeout = AnimationUtils.loadAnimation(const,R.anim.fadeout)
        val one_fadein = AnimationUtils.loadAnimation(const,R.anim.fadein)
        val fase_fadeout = AnimationUtils.loadAnimation(const,R.anim.fast_fade_out)
        val fase_fadein = AnimationUtils.loadAnimation(const,R.anim.fast_fade_in)


        binding.twinkle.startAnimation(one_fadeout)
        binding.twinkleTwo.startAnimation(one_fadeout)

        one_fadeout.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
               
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.twinkle.startAnimation(one_fadein)
                binding.twinkleTwo.startAnimation(one_fadein)
            }

            override fun onAnimationRepeat(animation: Animation?) {
               
            }

        })

           one_fadein.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.twinkle.startAnimation(one_fadeout)
                binding.twinkleTwo.startAnimation(one_fadeout)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

        binding.highlight.startAnimation(fase_fadeout)

        fase_fadeout.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.highlight.startAnimation(fase_fadein)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

           fase_fadein.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.highlight.startAnimation(fase_fadeout)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

        binding?.trophyAnimation1?.playAnimation()
        binding?.trophyAnimation2?.playAnimation()
        val anim3 = binding?.trophyAnimation4
        anim3?.playAnimation()
        binding?.trophyAnimation3?.playAnimation()
        binding?.trophyAnimation4?.playAnimation()
        anim3?.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
//                anim3.playAnimation()
//                binding?.trophyAnimation1?.playAnimation()
//                binding?.trophyAnimation3?.playAnimation()

                val fadeout = AnimationUtils.loadAnimation(const,R.anim.fadeout)
                
                binding.trophyAnimation1.startAnimation(fadeout)
                binding.trophyAnimation2.startAnimation(fadeout)
                binding.trophyAnimation3.startAnimation(fadeout)
                binding.trophyAnimation4.startAnimation(fadeout)
                fadeout.setAnimationListener(object : AnimationListener{
                    override fun onAnimationStart(animation: Animation?) {
                        
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        binding.trophyAnimation1.visibility = View.GONE
                        binding.trophyAnimation2.visibility = View.GONE
                        binding.trophyAnimation3.visibility = View.GONE
                        binding.trophyAnimation4.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                        
                    }

                })
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    private fun fetchWinnerList() {
        val  request = CommonRequest(
            userId = SessionManager(const).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(const).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(const).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(const).GetValue(Constants.VERSION_CODE)
        )

        viewModel.GetWinnerList(request).observe(const){
            binding.shimmer.startShimmer()
            binding.shimmer.visibility = View.GONE
            if(it.status == 200)
            {
                winnerDetailArrayList =it.winner_details as ArrayList<winnerDetail>
                if(!winnerDetailArrayList.isNullOrEmpty()) {
                    mAdapter = WinnerListAdapter(winnerDetailArrayList, const)
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

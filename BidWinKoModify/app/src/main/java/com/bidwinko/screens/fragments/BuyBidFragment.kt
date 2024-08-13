package com.bidwinko.screens.fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.adapter.BuyBidPlanAdapter
import com.bidwinko.databinding.BuybidFragmentBinding
import com.bidwinko.model.RequestModels.CommonRequest
import com.bidwinko.model.ResponseModels.BidPlan
import com.bidwinko.screens.activity.ReferActivity
import com.bidwinko.screens.activity.ShareEarnActivity
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel

class BuyBidFragment : Fragment() {

    lateinit var binding: BuybidFragmentBinding

    var recyclerView: RecyclerView? = null
    private var mAdapter: BuyBidPlanAdapter? = null
    var txtBidBalance: TextView? = null
    var unlimitedBid: CardView? = null
    private lateinit var const: FragmentActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            const = context
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        
        binding = BuybidFragmentBinding.inflate(layoutInflater)
        const.findViewById<TextView>(R.id.title).text = getString(R.string.buybid)
        recyclerView = binding.bidRecyclerView
        txtBidBalance = binding.bidbalance
        unlimitedBid = binding.cardUnlimitedBid
        binding.shimmer.root.startShimmer()
        binding.anim.let {
            it.playAnimation()
            it.addAnimatorListener(object : AnimatorListener{
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    it.playAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }

            })
        }
        binding.bidbalance.text = SessionManager(const).GetValue(Constants.TOTAL_BIDS)
        getBuyBids()
        unlimitedBid!!.setOnClickListener(View.OnClickListener { //				Toast.makeText(getActivity(),"Feature Coming Soon..",Toast.LENGTH_SHORT).show();
            val intent = Intent(activity, ReferActivity::class.java)
            startActivity(intent)
        })
        return binding.root
    }

    private fun getBuyBids() {
        val request = CommonRequest(
            userId = SessionManager(const).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(const).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(const).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(const).GetValue(Constants.VERSION_CODE)
        )
        mainViewModel(const).GetBidsPacakage(request).observe(const) {
            if (it.status == 200) {
                if (it.bidPlans.size > 0) {
                    mAdapter =
                        BuyBidPlanAdapter(it.bidPlans as ArrayList<BidPlan>, const)
                    binding.bidRecyclerView.setAdapter(mAdapter)
                    binding.bidRecyclerView.visibility = View.VISIBLE
                    binding.shimmer.root.stopShimmer()
                    binding.shimmer.root.visibility = View.GONE
                } else {
                    binding.shimmer.root.stopShimmer()
                    binding.shimmer.root.visibility = View.GONE
                    binding.nodatafound.visibility = View.VISIBLE
                    binding.animm.addAnimatorListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            binding.animm.playAnimation()
                        }

                        override fun onAnimationCancel(animation: Animator) {
                        }

                        override fun onAnimationRepeat(animation: Animator) {
                        }

                    })
                }
            }
        }
    }


    override fun onDestroy() {

        binding.shimmer.root.stopShimmer()
        super.onDestroy()
    }

    override fun onPause() {

        binding.shimmer.root.stopShimmer()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 103) {
            const.finish()
            startActivity(const.intent)
        }
    }

    companion object {
        fun newInstance(): BuyBidFragment {
            return BuyBidFragment()
        }
    }
}

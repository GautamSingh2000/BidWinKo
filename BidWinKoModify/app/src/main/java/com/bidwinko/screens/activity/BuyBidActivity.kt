package com.bidwinko.screens.activity

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.adapter.BuyBidPlanAdapter
import com.bidwinko.databinding.BuybidFragmentBinding
import com.bidwinko.model.RequestModels.CommonRequest
import com.bidwinko.model.ResponseModels.BidPlan
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel

class BuyBidActivity : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null
    private var mAdapter: BuyBidPlanAdapter? = null
    var txtBidBalance: TextView? = null
    var unlimitedBid: CardView? = null

    lateinit var binding : BuybidFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BuybidFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.black)))
        val upArrow = getResources().getDrawable(R.drawable.ic_arrow_back)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        (this as AppCompatActivity).supportActionBar!!.setTitle(
            Html.fromHtml(
                "<font color=\"#ffffff\">" + getString(
                    R.string.buybid
                ) + "</font>"
            )
        )
        txtBidBalance = findViewById(R.id.bidbalance)
        unlimitedBid = findViewById(R.id.card_unlimited_bid)
        binding.bidbalance.text = SessionManager(this).GetValue(Constants.TOTAL_BIDS)

        binding.buybidrefresh.setOnRefreshListener {
            binding.shimmer.root.startShimmer()
            binding.shimmer.root.visibility = View.VISIBLE
            binding.bidRecyclerView.visibility = View.GONE
            getData()
        }
        binding.cardUnlimitedBid.setOnClickListener {
            val intentbuybid = Intent(this, ReferActivity::class.java)
            startActivity(intentbuybid)
        }

        binding.choosetv.setOnClickListener {
            val intentbuybid = Intent(this, ReferActivity::class.java)
            startActivity(intentbuybid)
        }


        binding.anim.let {
            it.playAnimation()
            it.addAnimatorListener(object : AnimatorListener{
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    it.playAnimation()
                }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        getData()
    }

    private fun getData() {

        val request = CommonRequest(
            userId =  SessionManager(this).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(this).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(this).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(this).GetValue(Constants.VERSION_CODE)
        )
        mainViewModel(this).GetBidsPacakage(request).observe(this){
            binding.buybidrefresh.isRefreshing = false
            if(it.status == 200){
                SessionManager(this).InitializeValue(Constants.TOTAL_BIDS,it.totalBids)
                binding.bidbalance.text = SessionManager(this).GetValue(Constants.TOTAL_BIDS)
                    mAdapter = BuyBidPlanAdapter(it.bidPlans as ArrayList<BidPlan>, this@BuyBidActivity)
                binding.bidRecyclerView.setAdapter(mAdapter)
                binding.shimmer.root.stopShimmer()
                binding.shimmer.root.visibility = View.GONE
                binding.bidRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
    }

    public override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 103) {
            finish()
            startActivity(intent)
        }
    }
}

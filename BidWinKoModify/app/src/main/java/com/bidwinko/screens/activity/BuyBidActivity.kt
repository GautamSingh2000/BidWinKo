package com.bidwinko.screens.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.widget.TextView
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

        getData()
    }

    private fun getData() {

        if(!(this).isFinishing()) {
                        progressDialog = ProgressDialog(this);
                        progressDialog!!.setMessage(getString(R.string.loadingwait));
                        progressDialog!!.show();
                        progressDialog!!.setCancelable(false);
                    }

        val request = CommonRequest(
            userId =  SessionManager(this).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(this).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(this).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(this).GetValue(Constants.VERSION_CODE)
        )
        mainViewModel(this).GetBidsPacakage(request).observe(this){
            if(it.status == 200){
                 progressDialog?.dismiss()
                    mAdapter = BuyBidPlanAdapter(it.bidPlans as ArrayList<BidPlan>, this@BuyBidActivity)
                binding.bidRecyclerView.setAdapter(mAdapter)
            }
        }
    }

    private fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    public override fun onDestroy() {
        dismissProgressDialog()
        super.onDestroy()
    }

    public override fun onPause() {
        dismissProgressDialog()
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

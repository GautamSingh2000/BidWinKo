package com.bidwinko.screens.activity

import android.app.ProgressDialog
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.adapter.UserBidListAdapter
import com.bidwinko.databinding.ActivityMyBidBinding
import com.bidwinko.model.RequestModels.ProductDetailRequest
import com.bidwinko.model.ResponseModels.myBidsResponse
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.ItemAnimator
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel
import com.bumptech.glide.Glide

class MyBidActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyBidBinding
    var userBidArrayList = ArrayList<myBidsResponse.Bid>()
    private var recyclerView: RecyclerView? = null
    private var mAdapter: UserBidListAdapter? = null
    var progressDialog: ProgressDialog? = null
    var bidofferId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBidBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.black)))
        val upArrow = getResources().getDrawable(R.drawable.ic_arrow_back)
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        (this as AppCompatActivity).supportActionBar!!.title =
            Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.bidhistory) + "</font>")
        val intent = intent
        if (intent != null) {
            bidofferId = intent.getStringExtra("bidofferId")
        }
        bidofferId?.let { getUserBids(it) }
    }

    private fun getUserBids(bidofferId: String) {
        if (!(this@MyBidActivity).isFinishing()) {
            progressDialog = ProgressDialog(this@MyBidActivity)
            progressDialog!!.setMessage(getString(R.string.loadingwait))
            progressDialog!!.show()
            progressDialog!!.setCancelable(false)


            val request = ProductDetailRequest(
                bidId = bidofferId,
                userId = SessionManager(this).GetValue(Constants.USER_ID).toString(),
                securityToken = SessionManager(this).GetValue(Constants.SECURITY_TOKEN),
                versionName = SessionManager(this).GetValue(Constants.VERSION_NAME),
                versionCode = SessionManager(this).GetValue(Constants.VERSION_CODE)
            )

            mainViewModel(this).GetMyBids(request).observe(this) {
                dismissProgressDialog()
                if (it.status == 200) {
                    binding.title.text = it.bidTitle
                    Glide.with(this).load(it.bidImage).placeholder(R.drawable.placeholder)
                        .into(binding.productimage)
                    binding.cloatime.text = it.endDate
                    binding.Opentime.text = it.startDate
                    userBidArrayList.addAll(it.bids)
                    mAdapter = UserBidListAdapter(userBidArrayList, this)
                    binding.bidRecyclerView.adapter = mAdapter
                    binding.bidRecyclerView.setItemAnimator(ItemAnimator())
                    binding.filtrerbtn.setOnClickListener {
                      Toast.makeText(this,"Not Applicable for now!!",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Try Again Later !! " + it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
}


package com.bidwinko.screens.activity

import android.animation.Animator
import android.app.ProgressDialog
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.adapter.MYTransactionAdapter
import com.bidwinko.databinding.ActivityTransactionBinding
import com.bidwinko.model.RequestModels.CommonRequest
import com.bidwinko.model.ResponseModels.Transaction
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel

class TransactionActivity : AppCompatActivity() {
    var myTransactionModelArrayList: ArrayList<Transaction> = ArrayList()
    var myTransactionAdapter: MYTransactionAdapter? = null
    var recyclerView: RecyclerView? = null
    var progressDialog: ProgressDialog? = null
    lateinit var binding: ActivityTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.black)))
            val upArrow = getResources().getDrawable(R.drawable.ic_arrow_back)
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
            supportActionBar!!.setHomeAsUpIndicator(upArrow)
            (this as AppCompatActivity).supportActionBar!!.title = Html.fromHtml(
                "<font color=\"#ffffff\">" + getString(R.string.mytransaction) + "</font>"
            )
            recyclerView = findViewById<View>(R.id.bid_recycler_view) as RecyclerView
            getMyTransactions()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getMyTransactions() {
        if (!(this.isFinishing)) {
            progressDialog = ProgressDialog(this)
            progressDialog!!.setMessage(getString(R.string.loadingwait))
            progressDialog!!.show()
            progressDialog!!.setCancelable(false)
        }

        val homeRequest = CommonRequest(
            userId = SessionManager(this).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(this).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(this).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(this).GetValue(Constants.VERSION_CODE)
        )

        mainViewModel(this).GetTransactionHistory(homeRequest).observe(this)
        {
            dismissProgressDialog()
            if (it.status == 200) {
                if (!it.transactions.isNullOrEmpty() && it.transactions.size > 0) {
                    myTransactionModelArrayList.addAll(it.transactions)
                    recyclerView?.adapter = MYTransactionAdapter(myTransactionModelArrayList, this)
                } else {
                    binding.bidRecyclerView.visibility = View.GONE
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
            } else {
                binding.wrong.visibility = View.VISIBLE
                binding.anim.playAnimation()
                binding.anim.let {
                    it.addAnimatorListener(object : Animator.AnimatorListener {
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
                binding.bidRecyclerView.visibility = View.GONE
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

    override fun onPause() {
        dismissProgressDialog()
        super.onPause()
    }

    public override fun onStop() {
        dismissProgressDialog()
        super.onStop()
    }

    private fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }
}
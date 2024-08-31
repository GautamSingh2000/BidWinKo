package com.bidwinko.screens.activity

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.app.ProgressDialog
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.adapter.LoweRangeBidAdapter
import com.bidwinko.adapter.UpperRangeBidAdapter
import com.bidwinko.components.CustomDialog.CustomDialog
import com.bidwinko.components.CustomDialog.OnDialogActionListener
import com.bidwinko.databinding.ActivitySelectBidBinding
import com.bidwinko.model.RequestModels.PlaceBidRequest
import com.bidwinko.model.RequestModels.ProductDetailRequest
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel

class SelectBidActivity : AppCompatActivity(), UpperRangeBidAdapter.UpperBidRangeitemClickListener,
    LoweRangeBidAdapter.LowerBidRangeitemClickListener {

    val upperlist = ArrayList<String>()
    var lowerlist = ArrayList<String>()
    var pre_selected_bid = ArrayList<String>()
    var canceled_bid = ArrayList<String>()
    var bidsAvailable = 0
    var progressDialog: ProgressDialog? = null
    val lowerSelectedBidList = ArrayList<String>()
    private  var lowerListAdapter : LoweRangeBidAdapter? = null
    private  var upperListAdapter : UpperRangeBidAdapter? = null
    private lateinit var binding: ActivitySelectBidBinding
    var itemclick = "0"
    lateinit var bidId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBidBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.bidsAvailable.text = "${SessionManager(this).GetValue(Constants.TOTAL_BIDS)} Bids"

        binding.upShimmer.startShimmer()
        binding.lowShimmer.startShimmer()
        bidsAvailable = SessionManager(this).GetValue(Constants.TOTAL_BIDS).toInt()
        bidId = intent.getStringExtra("bidId").toString()
        getInitialUpperRange()
        lowerlist = generateLowerValues(0)
        getUserBid()

        binding.rangerv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val currentPosition = layoutManager.findFirstVisibleItemPosition()
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (currentPosition == 0) {
                    binding.prevbtn.isEnabled = false
                    binding.prevbtn.setCardBackgroundColor(resources.getColor(R.color.background_gray))
                    binding.prevbtnImg.imageTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.gray))
                } else {
                    binding.prevbtn.isEnabled = true
                    binding.prevbtn.setCardBackgroundColor(resources.getColor(R.color.darkgray))
                    binding.prevbtnImg.imageTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.white))
                }

                if (lastPosition >= upperlist.size - 1) {
                    binding.nextbtn.isEnabled = false
                    binding.nextbtn.setCardBackgroundColor(resources.getColor(R.color.background_gray))
                    binding.nextbtnimg.imageTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.gray))
                } else {
                    binding.nextbtn.isEnabled = true
                    binding.nextbtn.setCardBackgroundColor(resources.getColor(R.color.darkgray))
                    binding.nextbtnimg.imageTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.white))
                }

            }
        })

        binding.nextbtn.setOnClickListener {

            val layoutManager = binding.rangerv.layoutManager as? LinearLayoutManager
            layoutManager?.let {
                val nextItemPosition = it.findLastVisibleItemPosition() + 1
                val itemCount = binding.rangerv.adapter?.itemCount ?: 0

                binding.rangerv.smoothScrollToPosition(nextItemPosition)
                binding.nextbtn.isEnabled = true
                binding.nextbtn.visibility = View.VISIBLE
                binding.prevbtn.isEnabled = true

                if (nextItemPosition >= itemCount - 1) {
                    binding.nextbtn.isEnabled = false
                }
            }
        }

        binding.prevbtn.setOnClickListener {
            val layoutManager = binding.rangerv.layoutManager as? LinearLayoutManager
            layoutManager?.let {
                val previousItemPosition = it.findFirstVisibleItemPosition() - 1
                binding.rangerv.smoothScrollToPosition(previousItemPosition)
                binding.nextbtn.isEnabled = true
                binding.nextbtn.visibility = View.VISIBLE
                binding.prevbtn.isEnabled = true

                if (previousItemPosition <= 0) {
                    binding.prevbtn.isEnabled = false
                }
            }
        }

        binding.resetBtn.setOnClickListener {
            Reset()
        }

        binding.placeBid.setOnClickListener {
            if (lowerSelectedBidList.size != 0) {
                val padding = PaddingValues(
                    start = 80.dp,
                    top = 110.dp,
                    end = 80.dp,
                    bottom = 80.dp
                )
                CustomDialog(
                    context = this,
                    Title1 = "Do you want to place ${lowerSelectedBidList.size} Bids",
                    animationID = R.raw.confused,
                    positive = "Yes",
                    negative = "No",
                    repeat = true,
                    padding = padding,
                    onDialogActionListener = object : OnDialogActionListener {
                        override fun onPositiveButtonClick(dialog: CustomDialog) {
                            Log.e("selectbid","preed yes button")
                            PlaceBid()
                        }

                        override fun onNegativeButtonClick(dialog: CustomDialog) {
                            Log.e("selectbid","preed -ve button")
                        }

                        override fun onCancel(dialog: CustomDialog) {
                            Log.e("selectbid","preedno yes button")
                        }
                    }
                )
            } else {
                Toast.makeText(this, "Please Select At Least 1 Bid !!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun Reset() {
        binding.upShimmer.stopShimmer()
        binding.lowShimmer.stopShimmer()
        binding.upShimmer.visibility = View.VISIBLE
        binding.lowShimmer.visibility = View.VISIBLE
        binding.rangerv.visibility = View.GONE
        binding.allbidRv.visibility = View.GONE

        lowerSelectedBidList.clear()

        binding.bidsAvailable.text = "${SessionManager(this).GetValue(Constants.TOTAL_BIDS)} Bids"
        bidsAvailable = SessionManager(this).GetValue(Constants.TOTAL_BIDS).toInt()

        val anim = AnimationUtils.loadAnimation(this, R.anim.fast_rotate_clock_wise)
        binding.resetBtn.startAnimation(anim)
        anim.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                val numbers = generateLowerValues(0)
                lowerListAdapter = null
                lowerListAdapter = LoweRangeBidAdapter(
                    context = this@SelectBidActivity,
                    clickable = true,
                    list = numbers,
                    pre_selected_bids = pre_selected_bid,
                    canceled_bids = canceled_bid,
                    mListener = this@SelectBidActivity
                )
                binding.allbidRv.adapter = lowerListAdapter
                lowerListAdapter?.notifyDataSetChanged()


                upperListAdapter = null
                upperlist.clear()
                getInitialUpperRange()
                upperListAdapter = UpperRangeBidAdapter(this@SelectBidActivity, upperlist, this@SelectBidActivity)
                binding.rangerv.adapter = upperListAdapter
                binding.rangerv.smoothScrollToPosition(0)
                upperListAdapter?.notifyDataSetChanged()

                binding.placeBid.backgroundTintList =
                    ContextCompat.getColorStateList(this@SelectBidActivity, R.color.littledarkgray)
                binding.placeBid.setTextColor(ContextCompat.getColor(this@SelectBidActivity, R.color.white))

                binding.upShimmer.stopShimmer()
                binding.lowShimmer.stopShimmer()
                binding.upShimmer.visibility = View.GONE
                binding.lowShimmer.visibility = View.GONE
                binding.rangerv.visibility = View.VISIBLE
                binding.allbidRv.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
    }

    private fun getUserBid()  {
        val mainViewModel = mainViewModel(this)
        val request = ProductDetailRequest(
            bidId = bidId,
            userId = SessionManager(this).GetValue(Constants.USER_ID),
            securityToken = SessionManager(this).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(this).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(this).GetValue(Constants.VERSION_CODE),
        )
        mainViewModel.GetUserProductBid(request).observe(this@SelectBidActivity)
        {
            if (it.status == 200) {
                pre_selected_bid.addAll(it.userBids)
                canceled_bid.addAll(it.cancelledBids)
                Log.e("selectedbid", "${canceled_bid.size} size of canceled bids")
                Log.e("selectedbid", "${pre_selected_bid.size} size of pre_selected_bid")

                upperListAdapter = UpperRangeBidAdapter(this, upperlist, this)
                binding.rangerv.adapter = upperListAdapter

                lowerListAdapter = LoweRangeBidAdapter(
                    context = this,
                    clickable = true,
                    list = lowerlist,
                    pre_selected_bids = pre_selected_bid,
                    canceled_bids = canceled_bid,
                    mListener = this
                )
                binding.allbidRv.adapter = lowerListAdapter

                binding.upShimmer.stopShimmer()
                binding.lowShimmer.stopShimmer()
                binding.upShimmer.visibility = View.GONE
                binding.lowShimmer.visibility = View.GONE
               binding.rangerv.visibility = View.VISIBLE
               binding.allbidRv.visibility = View.VISIBLE
            }
        }
    }

    private fun PlaceBid() {
        Log.e("selectbid","PlaceBid APi on")
        if(!this.isFinishing)
        {
            progressDialog = ProgressDialog(this)
            progressDialog!!.setMessage(getString(R.string.loadingwait))
            progressDialog!!.show()
            progressDialog!!.setCancelable(false)

        }

        val placeBidRequest = PlaceBidRequest(
            bidId = bidId,
            bids = lowerSelectedBidList,
            userId = SessionManager(this).GetValue(Constants.USER_ID),
            securityToken = SessionManager(this).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(this).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(this).GetValue(Constants.VERSION_CODE)
        )

        val mainViewModel = mainViewModel(this)
        mainViewModel.PlaceBid(placeBidRequest).observe(this) {
            dismissProgressDialog()
            if (it.status == 200) {
                SessionManager(this).InitializeValue(Constants.TOTAL_BIDS,it.remainingBids)
                binding.party.let {
                    it.playAnimation()
                    it.addAnimatorListener(object : AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            finish()
                        }

                        override fun onAnimationCancel(animation: Animator) {
                        }

                        override fun onAnimationRepeat(animation: Animator) {
                        }

                    })
                }
                Toast.makeText(this@SelectBidActivity, "Congratulations !!", Toast.LENGTH_SHORT)
                    .show()
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(500)
            } else {
                Toast.makeText(this, "Sorry ${it.message}, Try Again Later !!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getInitialUpperRange() {
        for (i in 0..100) {
            upperlist.add(i.toString())
        }
    }

    fun generateLowerValues(inputNumber: Int): ArrayList<String> {
        val numbers = ArrayList<String>()

        for (i in inputNumber * 100..inputNumber * 100 + 99) {
            val number = i / 100.0
            numbers.add(number.toString())
        }
        return numbers
    }

    override fun onLowerBidRangeItemClick(item: String) {
        val found = lowerSelectedBidList.contains(item)
        if(canceled_bid.contains(item) || pre_selected_bid.contains(item)) {
            lowerListAdapter?.clickableBid(false)
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(200)
            Toast.makeText(this, "Sorry Not This One !!", Toast.LENGTH_LONG).show()
            return
        }
        if (bidsAvailable >= 1) {
            if (!found) {
                Log.e("SelectBid", "1 item selected")
                lowerSelectedBidList.add(item)
                bidsAvailable--
                lowerListAdapter?.clickableBid(true)
                binding.bidsAvailable.text = "$bidsAvailable Bids"
                binding.placeBid.let {
                    it.backgroundTintList =
                        ContextCompat.getColorStateList(this, R.color.lightgreen)
                    it.setTextColor(ContextCompat.getColor(this, R.color.green))
                }
            } else {
                Log.e("SelectBid", "2 item D selected")
                lowerSelectedBidList.remove(item)
                bidsAvailable++
                lowerListAdapter?.clickableBid(true)
                binding.bidsAvailable.text = "$bidsAvailable Bids"
                if (lowerSelectedBidList.size == 0) {
                    binding.placeBid.backgroundTintList =
                        ContextCompat.getColorStateList(this, R.color.littledarkgray)
                    binding.placeBid.setTextColor(ContextCompat.getColor(this, R.color.white))
                }
            }
        } else {
            if (found) {
                Log.e("SelectBid", "3 item D selected")
                lowerSelectedBidList.remove(item)
                bidsAvailable++
                lowerListAdapter?.clickableBid(true)
                binding.bidsAvailable.text = "$bidsAvailable Bids"
                if (lowerSelectedBidList.size == 0) {
                    binding.placeBid.backgroundTintList =
                        ContextCompat.getColorStateList(this, R.color.littledarkgray)
                    binding.placeBid.setTextColor(ContextCompat.getColor(this, R.color.white))
                }
            } else {
                lowerListAdapter?.clickableBid(false)
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(500)
                Toast.makeText(this, "No more bids are available!!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onUpperBidRangeItemClick(item: String, position: Int) {
        itemclick = item
//        upperRvPosition = position
        val numbers = generateLowerValues(itemclick.toInt())
        lowerListAdapter?.updateList(
            numbers,
            lowerSelectedBidList
        )

        lowerListAdapter?.notifyDataSetChanged()
    }

    private fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

}
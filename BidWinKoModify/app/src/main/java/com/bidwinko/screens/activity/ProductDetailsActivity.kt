package com.bidwinko.screens.activity

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.InputFilter
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.bidwinko.R
import com.bidwinko.components.Countdown.CountDown
import com.bidwinko.components.CustomDialog.CustomDialog
import com.bidwinko.components.CustomDialog.OnDialogActionListener
import com.bidwinko.databinding.ProductDetailsBinding
import com.bidwinko.model.RequestModels.ProductDetailRequest
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.DecimalDigitsInputFilter
import com.bidwinko.utilies.SessionManager
import com.bidwinko.utilies.convertDpToPx
import com.bidwinko.viewModel.mainViewModel
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductDetailsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ProductDetailsBinding
    lateinit var viewmodel: mainViewModel
    val countDown = CountDown()
    var bidofferId: String? = null
    var bidValue: String? = null
    var firstBid: String? = null
    var secondBid: String? = null
    var proImage: ImageView? = null
    var txt_productName: TextView? = null
    var txt_productPrice: TextView? = null
    var txt_starttime: TextView? = null
    var txt_desciption: TextView? = null
    var txt_currenttime: TextView? = null
    var txt_mytime: TextView? = null
    var txtmybid: TextView? = null
    var btn_plceBid: Button? = null
    var buybid: TextView? = null
    var hwbidwork: TextView? = null
    var txt_placebid: TextView? = null
    var txt_placebid_range: TextView? = null
    var txt_bidremain: TextView? = null
    var et_placebid: EditText? = null
    var et_firstbid_range: EditText? = null
    var et_secondbid_range: EditText? = null
    var progressDialog: ProgressDialog? = null
    var context: Context? = null
    var progressBar: ProgressBar? = null
    var image1 : String = ""
    var image2 : String = ""
    var image3 : String = ""
    var image4 : String = ""
    var i = 100
    var type = ""

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.black)))
        val upArrow = getResources().getDrawable(R.drawable.ic_arrow_back)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        (this as AppCompatActivity).supportActionBar!!.title = Html.fromHtml(
            "<font color=\"#ffffff\">" + getString(
                R.string.productdetails
            ) + "</font>"
        )
        val intent = intent
        if (intent != null) {
            bidofferId = intent.getIntExtra("bidofferId", -1).toString()
            type = intent.getStringExtra("type").toString()
        }

        //startService(new Intent(this, BroadcastService.class));
        // Log.e("testing", "Started service");

        txt_productName = findViewById(R.id.productname)
        hwbidwork = findViewById(R.id.bidwork)
        progressBar = findViewById(R.id.progressBar)
        progressBar?.progress = i
        txt_productPrice = findViewById(R.id.productprice)
        proImage = findViewById(R.id.productimage)
        txt_starttime = findViewById(R.id.starttime)
        et_placebid?.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(3, 2)))
        et_firstbid_range?.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(3, 2)))
        et_secondbid_range?.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(3, 2)))
        buybid = findViewById(R.id.buybid)
        txtmybid = findViewById(R.id.mybid)
        txtmybid?.setOnClickListener(this)
        buybid?.setOnClickListener(this)
        hwbidwork?.setOnClickListener(this)
        txt_placebid_range?.setOnClickListener(this)
        txt_placebid?.setOnClickListener(this)
        btn_plceBid = findViewById(R.id.place_bid)

        viewmodel = mainViewModel(this)
        bidofferId?.let { GetProductDetail(bidId = it) }


         binding.productdetailRefresh.setOnRefreshListener {
             bidofferId?.let { GetProductDetail(bidId = it) }
         }

        if(type.equals("upcomming"))
        {
            val leftPadding = convertDpToPx(0f,this)
            val topPadding = convertDpToPx(11f,this)
            val rightPadding = convertDpToPx(0f,this)
            val bottomPadding = convertDpToPx(11f,this)
            binding.ribbonTag.text="Upcoming"
            binding.liveAnim.setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
            binding.ribbon.imageTintList = getColorStateList(R.color.yellow)
            binding.liveAnim.setAnimation(R.raw.clock)
            binding.mybid.visibility = View.GONE
            binding.placeBid.setBackgroundColor(getColor(R.color.gray_4))
            binding.progressBar.visibility =View.GONE
            binding.profileLl.visibility =View.GONE

        }
        binding.liveAnim.let {
            it.playAnimation()
            it.addAnimatorListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    it.playAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
        }


        binding.bidwork.setOnClickListener {
           val alertDialog_layout = LayoutInflater.from(context).inflate(R.layout.how_to_bid, null)
            val builder = MaterialAlertDialogBuilder(this)
            builder.setView(alertDialog_layout)
          var alertDialog = builder.create()
            alertDialog.setCanceledOnTouchOutside(true)
            alertDialog.setCancelable(true)
            alertDialog.window?.setGravity(Gravity.CENTER)
            alertDialog.show()
        }

        binding.productimage.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(this, R.anim.fadeout)
            binding.otherimage.startAnimation(anim)
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    binding.otherimage.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }

        binding.sv.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // Check if the user is scrolling up or down
            if (scrollY > oldScrollY) {
                // Scrolling down
                binding.otherimage.visibility = View.VISIBLE
                TransitionManager.beginDelayedTransition(binding.otherimage)


            } else if (scrollY < oldScrollY) {
                // Scrolling up
                binding.otherimage.visibility = View.VISIBLE
                TransitionManager.beginDelayedTransition(binding.otherimage)
            }
        }
            binding.cv2.setOnClickListener { view ->
                    if(!image2.equals("")){
                    Glide.with(this).load(image2).into(binding.productimage)
                    Glide.with(this).load(image1).into(binding.productimage2)
                        var tem1 = image2
                        image2 = image1
                        image1 = tem1
                }else{}
            }

            binding.cv3.setOnClickListener { view ->
                if (!image3.equals("")){
                Glide.with(this).load(image3).into(binding.productimage)
                    Glide.with(this).load(image1).into(binding.productimage3)
                    var tem1 = image3
                    image3 = image1
                    image1 = tem1
                }else{}
            }

            binding.cv4.setOnClickListener { view ->
                if (!image4.equals("")) {
                    Glide.with(this).load(image4).into(binding.productimage)
                    Glide.with(this).load(image1).into(binding.productimage4)
                    var tem1 = image4
                    image4 = image1
                    image1 = tem1
                }else{}
            }

        btn_plceBid?.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun GetProductDetail(bidId: String) {

        val productDetailRequest = ProductDetailRequest(
            bidId = bidId,
            userId = SessionManager(this).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(this).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(this).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(this).GetValue(Constants.VERSION_CODE)
        )

        if (this.isFinishing == false) {
            progressDialog = ProgressDialog(this).apply {
                setMessage(getString(R.string.loadingwait))
                show()
                setCancelable(false)
            }
        }

        viewmodel.GetProductDetail(productDetailRequest).observe(this@ProductDetailsActivity) {
            dismissProgressDialog()
            binding.productdetailRefresh.isRefreshing = false
            if (it.status == 200) {
                Glide.with(this).load(it.bidDetails.productImage.get(0)).into(binding.productimage)
                SessionManager(this).InitializeValue(Constants.TOTAL_BIDS,it.totalBids)
                binding.noOfBid.text = it.totalBids
                image1 = it.bidDetails.productImage.get(0)
                binding.profilePeopleNo.text = " "
                if (it.bidDetails.productImage.size >= 2) {
                    binding.profilePeopleNo.text = "+${it.bidDetails.totalBid - 1}"
                    image2 = it.bidDetails.productImage.get(1)
                    binding.cv2.visibility = View.VISIBLE
                    Glide.with(this).load(it.bidDetails.productImage.get(1))
                        .into(binding.productimage2)
                }
                if (it.bidDetails.productImage.size >= 3) {
                    binding.profilePeopleNo.text = "+${it.bidDetails.totalBid - 2}"
                    image2 = it.bidDetails.productImage.get(1)
                    image3 = it.bidDetails.productImage.get(2)
                    binding.cv2.visibility = View.VISIBLE
                    Glide.with(this).load(it.bidDetails.productImage.get(1))
                        .into(binding.productimage2)
                    binding.cv3.visibility = View.VISIBLE
                    Glide.with(this).load(it.bidDetails.productImage.get(2))
                        .into(binding.productimage3)
                }
                if (it.bidDetails.productImage.size >= 4) {
                    binding.profilePeopleNo.text = "+${it.bidDetails.totalBid - 3}"
                    image2 = it.bidDetails.productImage.get(1)
                    image3 = it.bidDetails.productImage.get(2)
                    image4 = it.bidDetails.productImage.get(3)
                    
                    binding.cv2.visibility = View.VISIBLE
                    Glide.with(this).load(it.bidDetails.productImage.get(1))
                        .into(binding.productimage2)
                    binding.cv3.visibility = View.VISIBLE
                    Glide.with(this).load(it.bidDetails.productImage.get(2))
                        .into(binding.productimage3)
                    binding.cv4.visibility = View.VISIBLE
                    Glide.with(this).load(it.bidDetails.productImage.get(3))
                        .into(binding.productimage4)
                }
                binding.productname.text = it.bidDetails.productName
                binding.productprice.text = it.bidDetails.productPrice
                binding.features.text = it.bidDetails.productKeyFeature

                SetProfiles(it.bidDetails.bidder as ArrayList<String>)
                if(!type.equals("upcomming"))
                {
                    val count = CountDown()
                    count.start(
                        startTime = it.bidDetails.productStartTime.toLong(),
                        endTime = it.bidDetails.productEndTime.toLong(),
                        textView = binding.transactiontime,
                        seekBar = binding.progressBar
                    )
                }else{

                    val date = Date(it.bidDetails.productStartTime.toLong() * 1000)

                    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = format.format(date)

//                     val millisUntilFinished = it.bidDetails.productStartTime
//                    val days = millisUntilFinished / (1000 * 60 * 60 * 24)
//                    val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24
//                    val minutes = (millisUntilFinished / (1000 * 60)) % 60
//                    val seconds = (millisUntilFinished / 1000) % 60
//
//                    val parts = mutableListOf<String>()
//                    if (days > 0) parts.add("${days}d")
//                    if (hours > 0 || days > 0) parts.add("${hours}h")
//                    if (minutes > 0 || hours > 0 || days > 0) parts.add("${minutes}m")
//                    if(days == 0) {
//                        if (seconds > 0 || minutes > 0 || hours > 0 || days > 0) parts.add("${seconds}s")
//                    }
//                    val countdown = parts.joinToString(":")
                    binding.transactiontime.text = formattedDate
                }

            } else {
                val padding = PaddingValues(
                    start = 78.dp,
                    top = 78.dp,
                    end = 78.dp,
                    bottom = 78.dp
                )
                val NoDataFoundDialog = CustomDialog(
                    context = this,
                    Title1 = "Something Went Wrong !",
                    Title2 = "Restart This App Or Try Again Later",
                    cancelable = true,
                    Error = " Error : ${it.message}",
                    animationID = R.raw.api_wrong,
                    padding = padding,
                    repeat = false,
                    onDialogActionListener = object : OnDialogActionListener {
                        override fun onPositiveButtonClick(dialog: CustomDialog) {

                        }

                        override fun onNegativeButtonClick(dialog: CustomDialog) {

                        }

                        override fun onCancel(dialog: CustomDialog) {

                        }

                    }
                )
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.mybid -> {
                val intentmybid = Intent(this@ProductDetailsActivity, MyBidActivity::class.java)
                intentmybid.putExtra("bidofferId", bidofferId)
                startActivity(intentmybid)
            }

            R.id.buybid -> {
                val intentbuybid = Intent(this@ProductDetailsActivity, BuyBidActivity::class.java)
                startActivity(intentbuybid)
            }

            R.id.bidwork -> {
                val url = "https://bidwinzo.app/works.html"
               openTab(url)
            }

            R.id.place_bid -> {
                if (type.equals("upcomming")) {
                    Toast.makeText(this, "Will be live soon !", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this, SelectBidActivity::class.java)
                    intent.putExtra("bidId", bidofferId)
                    startActivity(intent)
                }

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

    override fun onStop() {
        try {
            countDown.stopCountDownTimer()
//            unregisterReceiver(br);
        } catch (e: Exception) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop()
    }

    fun openTab(url: String) {
        try {
            val builder = CustomTabsIntent.Builder()

            // to set the toolbar color use CustomTabColorSchemeParams
            // since CustomTabsIntent.Builder().setToolBarColor() is deprecated

            val params = CustomTabColorSchemeParams.Builder()
            params.setToolbarColor(ContextCompat.getColor(context!!, R.color.black))
            builder.setDefaultColorSchemeParams(params.build())
            // shows the title of web-page in toolbar
            builder.setShowTitle(true)

            // setShareState(CustomTabsIntent.SHARE_STATE_ON) will add a menu to share the web-page
            builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)

            // To modify the close button, use
            // builder.setCloseButtonIcon(bitmap)

            // to set weather instant apps is enabled for the custom tab or not, use
            builder.setInstantAppsEnabled(true)

            //  To use animations use -
            //  builder.setStartAnimations(this, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
            //  builder.setExitAnimations(this, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
            val customBuilder = builder.build()
            customBuilder.intent.setPackage("com.android.chrome")
            customBuilder.launchUrl(context!!, Uri.parse(url))
        }
        catch (e:Exception)
        {
            Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onPause() {
        dismissProgressDialog()
        countDown.stopCountDownTimer()
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun SetProfiles(list: ArrayList<String>) {
        if (list.size > 0) {
            when (list.size) {
                1 -> {
                    binding.profileimage1.visibility = View.GONE
                    binding.profileimage2.visibility = View.GONE
                    binding.profileimage3.visibility = View.GONE
                    Glide.with(this).load(list.get(0)).centerCrop().circleCrop()
                        .into(binding.profileimage4)
                }

                2 -> {

                    binding.profileimage1.visibility = View.GONE
                    binding.profileimage2.visibility = View.GONE
                    Glide.with(this).load(list.get(1)).centerCrop().circleCrop()
                        .into(binding.profileimage3)
                    Glide.with(this).load(list.get(0)).centerCrop().circleCrop()
                        .into(binding.profileimage4)
                }

                3 -> {
                    binding.profileimage1.visibility = View.GONE
                    Glide.with(this).load(list.get(2)).centerCrop().circleCrop()
                        .into(binding.profileimage2)
                    Glide.with(this).load(list.get(1)).centerCrop().circleCrop()
                        .into(binding.profileimage3)
                    Glide.with(this).load(list.get(0)).centerCrop().circleCrop()
                        .into(binding.profileimage4)
                }

                4 -> {
                    Glide.with(this).load(list.get(3)).centerCrop().circleCrop()
                        .into(binding.profileimage1)
                    Glide.with(this).load(list.get(2)).centerCrop().circleCrop()
                        .into(binding.profileimage2)
                    Glide.with(this).load(list.get(1)).centerCrop().circleCrop()
                        .into(binding.profileimage3)
                    Glide.with(this).load(list.get(0)).centerCrop().circleCrop()
                        .into(binding.profileimage4)
                }

                else -> {
                    Glide.with(this).load(list.get(3)).centerCrop().circleCrop()
                        .into(binding.profileimage1)
                    Glide.with(this).load(list.get(2)).centerCrop().circleCrop()
                        .into(binding.profileimage2)
                    Glide.with(this).load(list.get(1)).centerCrop().circleCrop()
                        .into(binding.profileimage3)
                    Glide.with(this).load(list.get(0)).centerCrop().circleCrop()
                        .into(binding.profileimage4)
                }
            }
        } else {
            binding.profileLl.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

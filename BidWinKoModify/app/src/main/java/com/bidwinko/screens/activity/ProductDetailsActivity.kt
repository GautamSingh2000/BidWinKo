package com.bidwinko.screens.activity

import android.animation.Animator
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bidwinko.R
import com.bidwinko.components.CircularProfileImagesInRow.CircularProfileInRowComponentAdapter
import com.bidwinko.databinding.ProductDetailsBinding
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.DecimalDigitsInputFilter
import com.bidwinko.utilies.convertDpToPx
import java.math.BigDecimal
import java.math.RoundingMode

class ProductDetailsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ProductDetailsBinding
    val countDown = com.bidwinko.components.CountDown()
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
    var i = 100

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        val intent = intent
        if (intent != null) {
            bidofferId = intent.getIntExtra("bidofferId", 0).toString()
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

//        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
//        progressBar.max = 100 // Set the maximum value of the progress bar
//        progressBar.progress = 75
        binding.progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                binding.progressBar.updateThumbText(progress.toString())
                // Update image if needed
                binding.progressBar.updateThumbImage(R.drawable.alarm)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.liveAnim.let {
            it.playAnimation()
            it.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    it.playAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        btn_plceBid?.setOnClickListener(this)
        //        getPrdocutDetails(bidofferId);
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
                val title = "How Bid Works"
                webViewLoad(url, title)
            }

            R.id.place_bid -> {
                val intent = Intent(this,SelectBidActivity::class.java)
                startActivity(intent)
            //                bidValue = et_placebid!!.getText().toString()
//                if (bidValue!!.isEmpty() || bidValue!!.length == 0 || bidValue == "" || bidValue == null) {
//                    et_placebid!!.error = "Enter bid value"
//                } else {
//                    if (bidValue!!.contains(".")) {
//                        val first = bidValue!![0].toString()
//                        val second = bidValue!!.substring(bidValue!!.length - 1)
//                        //                         Log.d("testing", "onClick:: "+first+"\n:"+second);
//                        if (first == "." || second == ".") {
//                            Toast.makeText(
//                                context,
//                                "Entered bid price is not correct, Valid bid price ex 01.25",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        } else {
//                            if (Constants.getSharedPreferenceString(
//                                    this@ProductDetailsActivity,
//                                    "bidBalance",
//                                    ""
//                                ).toInt() > 0
//                            ) {
//                                hideKeyboard(this@ProductDetailsActivity)
//                                //                                 submitSingleBid(bidofferId, bidValue);
//                            } else {
//                                Toast.makeText(
//                                    context,
//                                    getString(R.string.systemmessage) + "Not sufficient bids",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    } else {
//                        Toast.makeText(
//                            context,
//                            "Entered bid price is not correct, Valid bid price ex 01.25",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        et_placebid!!.isFocusable = true
//                    }
//                }
            }

//            R.id.tv_placebid_range -> {
//                firstBid = et_firstbid_range!!.getText().toString()
//                secondBid = et_secondbid_range!!.getText().toString()
//                invalidateBidRange(firstBid!!, secondBid!!, bidofferId)
//            }
        }
    }

    //    private void submitSingleBid(String bidofferId,String bidValue) {
    //        APIService apiService = Retrofit.getClient().create(APIService.class);
    //        Call<SingleBidSubmitModel> call = apiService.submitSingleBid(Constants.getSharedPreferenceInt(ProductDetailsActivity.this,"userId",0),
    //                Constants.getSharedPreferenceString(ProductDetailsActivity.this,"securitytoken",""),
    //                Constants.getSharedPreferenceString(ProductDetailsActivity.this,"versionName",""),
    //                Constants.getSharedPreferenceInt(ProductDetailsActivity.this,"versionCode",0),bidValue,bidofferId,
    //                Constants.getSharedPreferenceString(ProductDetailsActivity.this,"userFrom",""));
    //
    //        if(!((Activity) ProductDetailsActivity.this).isFinishing()) {
    //            progressDialog = new ProgressDialog(ProductDetailsActivity.this);
    //            progressDialog.setMessage(getString(R.string.loadingwait));
    //            progressDialog.show();
    //            progressDialog.setCancelable(false);
    //        }
    //
    //        call.enqueue(new Callback<SingleBidSubmitModel>() {
    //            @Override
    //            public void onResponse(Call<SingleBidSubmitModel>call, Response<SingleBidSubmitModel> response) {
    //                dismissProgressDialog();
    //
    //                if(response!=null){
    //                    if(response.isSuccessful()){
    //                        if(response.body().getStatus()==200){
    ////                            Log.e("tesitng", "onResponse: "+response.body().getDisplayMsg() );
    //                            Toast.makeText(context,response.body().getDisplayMsg(),Toast.LENGTH_SHORT).show();
    //                            et_placebid.setText("");
    //                            String bidremain = response.body().getBidBalance();
    //                            Log.e("bidremain", "onResponse:pd_sB "+bidremain );
    //                            Constants.setSharedPreferenceString(ProductDetailsActivity.this,"bidBalance",bidremain);
    //                            txt_bidremain.setText(Constants.getSharedPreferenceString(ProductDetailsActivity.this,"bidBalance","")+" Bids Remaining");
    //
    //                        }else{
    //                            Toast.makeText(context,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
    //                        }
    //
    //                    }
    //                }
    //                else{
    //                    Toast.makeText(context,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
    //                }
    //
    //
    //            }
    //
    //            @Override
    //            public void onFailure(Call<SingleBidSubmitModel>call, Throwable t) {
    //                // Log error here since request failed
    //                Log.e("response", t.toString());
    //            }
    //        });
    //    }
    //
    //    private void submitBidFromRange(String firstBid,String secondBid,String bidofferId) {
    //        APIService apiService = Retrofit.getClient().create(APIService.class);
    //        Call<SingleBidSubmitModel> call = apiService.submitBidfromTo(Constants.getSharedPreferenceInt(ProductDetailsActivity.this,"userId",0),
    //                Constants.getSharedPreferenceString(ProductDetailsActivity.this,"securitytoken",""),
    //                Constants.getSharedPreferenceString(ProductDetailsActivity.this,"versionName",""),
    //                Constants.getSharedPreferenceInt(ProductDetailsActivity.this,"versionCode",0),firstBid,secondBid,bidofferId,
    //                Constants.getSharedPreferenceString(ProductDetailsActivity.this,"userFrom",""));
    //
    //        if(!((Activity) ProductDetailsActivity.this).isFinishing()) {
    //            progressDialog = new ProgressDialog(ProductDetailsActivity.this);
    //            progressDialog.setMessage(getString(R.string.loadingwait));
    //            progressDialog.show();
    //            progressDialog.setCancelable(false);
    //        }
    //
    //        call.enqueue(new Callback<SingleBidSubmitModel>() {
    //            @Override
    //            public void onResponse(Call<SingleBidSubmitModel>call, Response<SingleBidSubmitModel> response) {
    //                dismissProgressDialog();
    //
    //                if(response!=null){
    //                    if(response.isSuccessful()){
    //                        if(response.body().getStatus()==200){
    //                            Toast.makeText(context,getString(R.string.systemmessage)+response.body().getDisplayMsg(),Toast.LENGTH_SHORT).show();
    //                            et_firstbid_range.setText("");
    //                            et_secondbid_range.setText("");
    //                            String bidremain = response.body().getBidBalance();
    //                            Log.e("bidremain", "onResponse:pd "+bidremain );
    //                            Constants.setSharedPreferenceString(ProductDetailsActivity.this,"bidBalance",bidremain);
    //                            txt_bidremain.setText(Constants.getSharedPreferenceString(ProductDetailsActivity.this,"bidBalance","")+" Bids Remaining");
    //
    //
    //                        }else{
    //                            Toast.makeText(context,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
    //                        }
    //
    //                    }
    //                }
    //                else{
    //                    Toast.makeText(context,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
    //                }
    //
    //
    //            }
    //
    //            @Override
    //            public void onFailure(Call<SingleBidSubmitModel>call, Throwable t) {
    //                // Log error here since request failed
    //                Log.e("response", t.toString());
    //            }
    //        });
    //    }
    private fun invalidateBidRange(firstBid: String, secondBid: String, bidofferId: String?) {
        if (firstBid.matches("".toRegex()) && secondBid.matches("".toRegex())) {
            et_firstbid_range!!.error = "Enter bid value"
            et_secondbid_range!!.error = "Enter bid value"
            return
        } else if (firstBid.matches("".toRegex())) {
            et_firstbid_range!!.error = "Enter bid value"
            return
        } else if (secondBid.matches("".toRegex())) {
            et_secondbid_range!!.error = "Enter bid value"
            return
        } else {
            try {
                if (firstBid.contains(".") && secondBid.contains(".")) {
                    val firstbidValue = firstBid.toDouble()
                    val secondbidValue = secondBid.toDouble()
                    val diffrence = secondbidValue - firstbidValue
                    val bd = BigDecimal(diffrence).setScale(2, RoundingMode.HALF_UP)
                    val newInput = bd.toDouble()
                    Log.e("values", "invalidateBidRange: $diffrence newInput: $newInput")
                    //for first bid range
                    val firstValue = firstBid[0].toString()
                    val firsr_bid_range = firstBid.substring(firstBid.length - 1)
                    //for second bid range
                    val secondValue = secondBid[0].toString()
                    val second_bid_range = secondBid.substring(secondBid.length - 1)
                    if (firstValue == "." || firsr_bid_range == "." || secondValue == "." || second_bid_range == ".") {
                        Toast.makeText(
                            context,
                            "Enter correct bid range like ex 01.25 to ex 01.35",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (newInput > 0.0) {
                            if (newInput <= 0.10) {
                                if (Constants.getSharedPreferenceString(
                                        this@ProductDetailsActivity,
                                        "bidBalance",
                                        ""
                                    ).toInt() > 0
                                ) {
                                    hideKeyboard(this@ProductDetailsActivity)
                                    //                                        submitBidFromRange(firstBid, secondBid, bidofferId);
                                } else {
                                    Toast.makeText(
                                        context,
                                        getString(R.string.systemmessage) + "Not sufficient bids",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else Toast.makeText(
                                context,
                                "Enter correct bid range like ex 01.25 to ex 01.35",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else Toast.makeText(
                            context,
                            "Enter correct bid range like ex 01.25 to ex 01.35",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Entered bid range price is not correct, Valid bid price ex 01.25 to 01.35",
                        Toast.LENGTH_SHORT
                    ).show()
                    //et_placebid.setFocusable(true);
                }
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
                Log.e("numbervalue", "invalidateBidRange: $nfe")
            }
        }
    }

    private fun webViewLoad(url: String, title: String) {
        val alert = AlertDialog.Builder(this@ProductDetailsActivity)
        alert.setTitle(title)
        val wv = WebView(this@ProductDetailsActivity)
        wv.loadUrl(url)
        wv.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        })
        alert.setView(wv)
        alert.setNegativeButton("Accept") { dialog, id -> dialog.dismiss() }
        alert.show()
    }

    //    private void getPrdocutDetails(final String bidofferId) {
    //
    //        APIService apiService = Retrofit.getClient().create(APIService.class);
    //        Call<ProductsDetailsModel> call = apiService.getProductDetails(Constants.getSharedPreferenceInt(ProductDetailsActivity.this,"userId",0),
    //                Constants.getSharedPreferenceString(ProductDetailsActivity.this,"securitytoken",""),
    //                Constants.getSharedPreferenceString(ProductDetailsActivity.this,"versionName",""),
    //                Constants.getSharedPreferenceInt(ProductDetailsActivity.this,"versionCode",0),bidofferId,
    //                Constants.getSharedPreferenceString(ProductDetailsActivity.this,"userFrom",""));
    //
    //
    //
    //        if(!((Activity) ProductDetailsActivity.this).isFinishing()) {
    //            progressDialog = new ProgressDialog(ProductDetailsActivity.this);
    //            progressDialog.setMessage(getString(R.string.loadingwait));
    //            progressDialog.show();
    //            progressDialog.setCancelable(false);
    //        }
    //
    //        call.enqueue(new Callback<ProductsDetailsModel>() {
    //            @Override
    //            public void onResponse(Call<ProductsDetailsModel>call, Response<ProductsDetailsModel> response) {
    //                dismissProgressDialog();
    //
    //                if(response!=null){
    //                    if(response.isSuccessful()){
    //                        if(response.body().getStatus()==200){
    //
    //                            ArrayList<BidofferDetails> offerDetails = new ArrayList<>();
    //                            offerDetails.add(response.body().getBidofferDetails());
    //
    //                            String prodName="",prodPrice="",statime="",desciption="",bidtime ="",bidremain="";
    //                            int bidlefttime = 0;
    //
    //                            RequestOptions requestOptions = new RequestOptions();
    //                            requestOptions.placeholder(R.drawable.placeholder);
    //                            requestOptions.error(R.drawable.placeholder);
    //
    //                            for(int i=0 ;i<offerDetails.size(); i++){
    //                                prodName = offerDetails.get(i).getBidofferName();
    //                                prodPrice = offerDetails.get(i).getBidofferPrice();
    //                                statime = offerDetails.get(i).getStartTime();
    //                                desciption = offerDetails.get(i).getOfferDescription();
    //                                bidtime = offerDetails.get(i).getStartTime();
    //                                bidlefttime = offerDetails.get(i).getOfferTimeLeft();
    //                                bidremain = offerDetails.get(i).getBidBalance();
    //                                Log.e("bidremain", "onResponse:pd_list "+bidremain );
    //                                Constants.setSharedPreferenceString(ProductDetailsActivity.this,"bidBalance",bidremain);
    //                                txt_bidremain.setText(Constants.getSharedPreferenceString(ProductDetailsActivity.this,"bidBalance","")+" Bids Remaining");
    //
    //                                Glide.with(context)
    //                                        .setDefaultRequestOptions(requestOptions)
    //                                        .load(offerDetails.get(i).getImageUrl()).into(proImage);
    //                            }
    //
    //                            txt_productName.setText(prodName);
    //                            txt_productPrice.setText(prodPrice);
    //                            txt_starttime.setText(statime);
    //                            txt_desciption.setText(desciption);
    //
    //                            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    //                            txt_mytime.setText(currentDateTimeString);
    //                            txt_currenttime.setText(bidtime);
    //                            Log.e("testing", "onResponse:left time "+bidlefttime );
    //                            //progressBar.setProgress(bidlefttime);
    ////                            getProgressBarStatus(i);
    //                            getCounter(bidlefttime);
    //
    //
    //
    //                        }else{
    //                            Toast.makeText(context,getString(R.string.systemmessage)+response.body().getMessage(),Toast.LENGTH_SHORT).show();
    //                        }
    //
    //                    }
    //                }
    //                else{
    //                    Toast.makeText(context,getString(R.string.systemmessage)+response.errorBody(),Toast.LENGTH_SHORT).show();
    //                }
    //
    //
    //            }
    //
    //            @Override
    //            public void onFailure(Call<ProductsDetailsModel>call, Throwable t) {
    //                // Log error here since request failed
    //                Log.e("response", t.toString());
    //            }
    //        });
    //
    //    }
//    fun getCounter(bidlefttime: Int) {
//        val millisInFuture: Long = 100000
//        val countDownInterval: Long = 1000
//        val progressBarMaximumValue = (millisInFuture / countDownInterval).toInt()
//        Log.e("Log_tag", "getCounter: $progressBarMaximumValue")
//        progressBar!!.setMax(progressBarMaximumValue)
//        mCountDownTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
//            override fun onTick(millisUntilFinished: Long) {
//                val TimeLeftInMillis = millisUntilFinished / 1000
//                Log.e("Log_tag", "onTick:TimeLeftInMillis $TimeLeftInMillis")
//                i--
//                progressBar!!.progress = TimeLeftInMillis.toInt()
//            }
//
//            override fun onFinish() {
//                //Do what you want
//                i--
//                progressBar!!.progress = i
//            }
//        }
//        mCountDownTimer?.start()
//    }

    //    private BroadcastReceiver br = new BroadcastReceiver() {
    //        @Override
    //        public void onReceive(Context context, Intent intent) {
    //            updateGUI(intent); // or whatever method used to update your GUI fields
    //        }
    //    };
    //    private void updateGUI(Intent intent) {
    //        if (intent.getExtras() != null) {
    //            long millisUntilFinished = intent.getLongExtra("countdown", 0);
    //            Log.e("testing", "Countdown seconds remaining: " +  millisUntilFinished / 1000);
    //            String timelef = String.valueOf(millisUntilFinished/1000);
    //            txt_mytime.setText(timelef);
    ////            progressBar.setProgress(Integer.parseInt(timelef));
    //        }
    //    }
    private fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    public override fun onDestroy() {
        dismissProgressDialog()
        //        stopService(new Intent(this, BroadcastService.class));
//        Log.e("testing", "Stopped service");
        super.onDestroy()
    }

    override fun onStop() {
        try {

//            unregisterReceiver(br);
        } catch (e: Exception) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop()
    }

    public override fun onPause() {
        dismissProgressDialog()

//        mCountDownTimer.cancel();
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun SetProfiles(list: ArrayList<String>) {
        if (list.size > 0) {
            binding.profilesRv.let {
                it.adapter = CircularProfileInRowComponentAdapter(this, list)
                it.setTransitionVisibility(View.VISIBLE)
            }
            binding.profilePeopleNo.text = "${list.size}+"
        } else {
            binding.profileLl.visibility = View.GONE
        }
    }

    fun View.overlapProfile(dp: Float) {
        val px = convertDpToPx(dp, context)
        val layoutParams = this.layoutParams as MarginLayoutParams
        layoutParams.setMargins(0, 0, px, 0)
        this.layoutParams = layoutParams
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

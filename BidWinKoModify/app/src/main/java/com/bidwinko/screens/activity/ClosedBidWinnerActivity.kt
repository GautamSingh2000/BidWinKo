package com.bidwinko.screens.activity

import android.animation.Animator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bidwinko.R
import com.bidwinko.adapter.CLosedBidWinnerAdapter
import com.bidwinko.components.NoDataFound.NoDataFound
import com.bidwinko.databinding.ActivityClosedBidWinnerBinding
import com.bidwinko.model.RequestModels.ClosedBidWinnerRequest
import com.bidwinko.model.ResponseModels.ClosedWinnerList
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel
import com.bumptech.glide.Glide


lateinit var binding: ActivityClosedBidWinnerBinding
var image1 : String = ""
var image2 : String = ""
var image3 : String = ""
var image4 : String = ""
private var productID: String = ""
class ClosedBidWinnerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClosedBidWinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.black)))
        val upArrow = getResources().getDrawable(R.drawable.ic_arrow_back)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        (this as AppCompatActivity).supportActionBar!!.setTitle(
            Html.fromHtml(
                "<font color=\"#ffffff\">" + getString(
                    R.string.closed_bid_winners
                ) + "</font>"
            )
        )

        val intent = intent
        if (intent != null) {
            productID = intent.getIntExtra("bidofferId", -1).toString()
        }

       Log.e("closebidActi","$productID id ")
        if(!productID.equals(""))
        {
            GetData()
        }else{
        }
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

        binding.productimage.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(this, R.anim.fast_fade_out)
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


    }

    fun GetData()
    {
        val request = ClosedBidWinnerRequest(
            bidId = productID,
            userId = SessionManager(this).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(this).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(this).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(this).GetValue(Constants.VERSION_CODE)
        )
        
        val mainViewModel = mainViewModel(this)
        mainViewModel.GetClosedWinnersList(request).observe(this){
            if(it.status == 200){
                Glide.with(this).load(it.productDetail.productImage.get(0)).into(binding.productimage)
                image1 = it.productDetail.productImage.get(0)
                if (it.productDetail.productImage.size >= 2) {
                    binding.otherimage.visibility = View.VISIBLE
                    image2 = it.productDetail.productImage.get(1)
                    binding.cv2.visibility = View.VISIBLE
                    Glide.with(this).load(it.productDetail.productImage.get(1))
                        .into(binding.productimage2)
                }
                if (it.productDetail.productImage.size >= 3) {
                    binding.otherimage.visibility = View.VISIBLE
                    image2 = it.productDetail.productImage.get(1)
                    image3 = it.productDetail.productImage.get(2)
                    binding.cv2.visibility = View.VISIBLE
                    Glide.with(this).load(it.productDetail.productImage.get(1))
                        .into(binding.productimage2)
                    binding.cv3.visibility = View.VISIBLE
                    Glide.with(this).load(it.productDetail.productImage.get(2))
                        .into(binding.productimage3)
                }
                if (it.productDetail.productImage.size >= 4) {

                    binding.otherimage.visibility = View.VISIBLE
                    image2 = it.productDetail.productImage.get(1)
                    image3 = it.productDetail.productImage.get(2)
                    image4 = it.productDetail.productImage.get(3)

                    binding.cv2.visibility = View.VISIBLE
                    Glide.with(this).load(it.productDetail.productImage.get(1))
                        .into(binding.productimage2)
                    binding.cv3.visibility = View.VISIBLE
                    Glide.with(this).load(it.productDetail.productImage.get(2))
                        .into(binding.productimage3)
                    binding.cv4.visibility = View.VISIBLE
                    Glide.with(this).load(it.productDetail.productImage.get(3))
                        .into(binding.productimage4)
                }

                binding.name.text = it.productDetail.productName
                binding.endingTime.text =it.productDetail.productEndTime
                binding.price.text = it.productDetail.productPrice

                if(!it.winnersList.isNullOrEmpty())
                {
                    val adapter = CLosedBidWinnerAdapter(it.winnersList as ArrayList<ClosedWinnerList>,this)
                    binding.wiinerRv.adapter = adapter
                }else{
                    binding.wiinerRv.visibility = View.GONE
                    binding.nodatafound.let {
                        it.anim.playAnimation()
                        it.anim.addAnimatorListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator) {
                            }
                            override fun onAnimationEnd(animation: Animator) {
                                it.anim.playAnimation()
                            }
                            override fun onAnimationCancel(animation: Animator) {
                            }
                            override fun onAnimationRepeat(animation: Animator) {
                            }

                        })
                    }
                    binding.nodatafound.root.visibility = View.VISIBLE

                }
            }else{
                
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
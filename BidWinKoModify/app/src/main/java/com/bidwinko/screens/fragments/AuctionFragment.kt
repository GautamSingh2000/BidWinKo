package com.bidwinko.screens.fragments

import android.Manifest
import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.LayoutTransition
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.bidwinko.R
import com.bidwinko.adapter.LiveBidProductsListAdapter
import com.bidwinko.components.BannerImageSlider.ImageItem
import com.bidwinko.components.BannerImageSlider.bannerImageAdapter
import com.bidwinko.databinding.FragmentHomeBinding
import com.bidwinko.model.RequestModels.CommonRequest
import com.bidwinko.model.ResponseModels.AppBanner
import com.bidwinko.model.ResponseModels.HomeList
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel


private const val CHANNEL_ID = "notification_channel_id"

private lateinit var viewpager2: ViewPager2
private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
private val params = LinearLayout.LayoutParams(
    LinearLayout.LayoutParams.WRAP_CONTENT,
    LinearLayout.LayoutParams.WRAP_CONTENT
).apply {
    setMargins(8, 0, 8, 0)
}
var CardSelected = "Live"
private lateinit var update: Runnable


lateinit var binding: FragmentHomeBinding

private lateinit var const: FragmentActivity

class AuctionFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            const = context
        }
    }

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(context, "Notification permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private lateinit var viewmodel: mainViewModel
    var LiveBidList = ArrayList<HomeList>()
    var ClosedBidList = ArrayList<HomeList>()
    var UpcommingBidList = ArrayList<HomeList>()
    var banner_list = ArrayList<ImageItem>()
    private var mAdapter: LiveBidProductsListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        const.findViewById<TextView>(R.id.title).setText(R.string.current_auction)
        viewmodel = mainViewModel(const)
        binding = FragmentHomeBinding.inflate(layoutInflater)


        handleNotificationPermission()

        binding.homeRefresh.setOnRefreshListener {

            binding.liveBtn.alpha  = 1f
            binding.upcomingBtn.alpha  = 0.7f
            binding.closeBtn.alpha  = 0.7f

            binding.liveBtnText.setTextColor(const.getColor(R.color.black))
            binding.liveBtnText.background =
                const.getDrawable(R.drawable.white_rounded_bg)

            binding.upcomingBtnText.setTextColor(const.getColor(R.color.white))
            binding.upcomingBtnText.background =
                const.getDrawable(R.drawable.white_holo_bg)

            binding.closeBtnText.setTextColor(const.getColor(R.color.white))
            binding.closeBtnText.background = const.getDrawable(R.drawable.white_holo_bg)

            CardSelected = "Live"
            val homeRequest = CommonRequest(
                userId = SessionManager(const).GetValue(Constants.USER_ID),
                securityToken = SessionManager(const).GetValue(Constants.SECURITY_TOKEN),
                versionName = SessionManager(const).GetValue(Constants.VERSION_NAME),
                versionCode = SessionManager(const).GetValue(Constants.VERSION_CODE)
            )
            GetLiveData(homeRequest)

        }

        binding.anim.let {
            it.playAnimation()
            it.addAnimatorListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    it.playAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}

            })
        }
        viewpager2 = binding.latestDealslider
        setAnimation()
        GetHomeData()

        binding.upcomingBtn.setOnClickListener {

            it.alpha = 1f
            CardSelected = "Upcoming"
            val homeRequest = CommonRequest(
                userId = SessionManager(const).GetValue(Constants.USER_ID).toString(),
                securityToken = SessionManager(const).GetValue(Constants.SECURITY_TOKEN),
                versionName = SessionManager(const).GetValue(Constants.VERSION_NAME),
                versionCode = SessionManager(const).GetValue(Constants.VERSION_CODE)
            )

            GetUpcommingData(homeRequest)

        }


        binding.liveBtn.setOnClickListener {
            CardSelected = "Live"

            val homeRequest = CommonRequest(
                userId = SessionManager(const).GetValue(Constants.USER_ID).toString(),
                securityToken = SessionManager(const).GetValue(Constants.SECURITY_TOKEN),
                versionName = SessionManager(const).GetValue(Constants.VERSION_NAME),
                versionCode = SessionManager(const).GetValue(Constants.VERSION_CODE)
            )
            it.alpha  = 1f
            GetLiveData(homeRequest)
        }

        binding.closeBtn.setOnClickListener {
            CardSelected = "Closed"

            val homeRequest = CommonRequest(
                userId = SessionManager(const).GetValue(Constants.USER_ID).toString(),
                securityToken = SessionManager(const).GetValue(Constants.SECURITY_TOKEN),
                versionName = SessionManager(const).GetValue(Constants.VERSION_NAME),
                versionCode = SessionManager(const).GetValue(Constants.VERSION_CODE)
            )

            GetCompletedData(homeRequest)
        }

        binding.howToBidBtn.setOnClickListener {
            val textView = binding.howToBidBtnText
            if (binding.biddingsteps.visibility == View.VISIBLE) {
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ContextCompat.getDrawable(
                        const, R.drawable.down_btn
                    ), null
                )
                binding.howToBidBtn.setCardBackgroundColor(resources.getColor(R.color.colorAccent))
                binding.biddingsteps.visibility = View.GONE
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ContextCompat.getDrawable(
                        const, R.drawable.up
                    ), null
                )
                val transition = LayoutTransition()
                transition.setAnimateParentHierarchy(true)
                binding.biddingsteps.layoutTransition = transition

                binding.howToBidBtn.setCardBackgroundColor(resources.getColor(R.color.gray_4))
                binding.biddingsteps.visibility = View.VISIBLE
                binding.scrollview.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        binding.scrollview.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        binding.scrollview.smoothScrollTo(0, binding.step1Ll.bottom)
                    }
                })
            }
        }

        return binding.root
    }

    private fun GetHomeData() {
        val homeRequest = CommonRequest(
            userId = SessionManager(const).GetValue(Constants.USER_ID).toString(),
            securityToken = SessionManager(const).GetValue(Constants.SECURITY_TOKEN),
            versionName = SessionManager(const).GetValue(Constants.VERSION_NAME),
            versionCode = SessionManager(const).GetValue(Constants.VERSION_CODE)
        )
        viewmodel.GetHomeData(homeRequest).observe(const) {
            if (it.status == 200) {
                setBannerData(it.appBanners)
                GetLiveData(homeRequest)
            } else {
                Log.e(tag, "banner apis error ${it.message}")
                Toast.makeText(
                    const,
                    "Somthing Wenr Wrong!! ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
    }

    private fun setBannerData(bannerlist: List<AppBanner>) {
        banner_list.clear()
        for (banner in bannerlist) {
            banner_list.add(ImageItem(banner.id.toString(), banner.image))
        }
        val imageAdapter = bannerImageAdapter()
        viewpager2.adapter = imageAdapter
        imageAdapter.submitList(banner_list)
        binding.shimmer1.stopShimmer()
        binding.shimmer1.visibility = View.GONE

        val dotsImage = Array(banner_list.size) { ImageView(const) }

        dotsImage.forEach {
            it.setImageResource(
                R.drawable.dot
            )
            binding.slideDotLL.addView(it, params)
        }

        // default first dot selected
        if (dotsImage.size > 0 && dotsImage.isNullOrEmpty()) dotsImage[0].setImageResource(R.drawable.dot)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index) {
                        imageView.setImageResource(
                            R.drawable.dot
                        )
                    } else {
                        imageView.setImageResource(R.drawable.dot)
                    }
                }
                super.onPageSelected(position)
            }
        }
        viewpager2.registerOnPageChangeCallback(pageChangeListener)

        val handler = Handler()
        update = Runnable {
            val currentPage = viewpager2.currentItem
            if (currentPage == banner_list.size - 1) {
                viewpager2.setCurrentItem(0, true) // reset to first page
            } else {
                viewpager2.setCurrentItem(currentPage + 1, true)
            }
            handler.postDelayed(update, 3500) // self-schedule again
        }
        handler.postDelayed(update, 3500) // initial schedule
    }


    private fun setAnimation() {

        val leftslidein = AnimationUtils.loadAnimation(const, R.anim.left_slide_in)
        val leftslideout = AnimationUtils.loadAnimation(const, R.anim.left_slide_out)
        val rightslidein = AnimationUtils.loadAnimation(const, R.anim.right_slide_in)
        val rightslideout = AnimationUtils.loadAnimation(const, R.anim.right_slide_out)

        val slowleftslidein = AnimationUtils.loadAnimation(const, R.anim.slowleftin)
        val slowleftslideout = AnimationUtils.loadAnimation(const, R.anim.slowleftout)
        val slowrightslidein = AnimationUtils.loadAnimation(const, R.anim.slowrightin)
        val slowrightslideout = AnimationUtils.loadAnimation(const, R.anim.slowrightout)

        val fadein = AnimationUtils.loadAnimation(const, R.anim.fadein)
        val fadeout = AnimationUtils.loadAnimation(const, R.anim.fadeout)
        val rotate = AnimationUtils.loadAnimation(const, R.anim.clockwise_rotate)
        val antirotate = AnimationUtils.loadAnimation(const, R.anim.aniclock_rotate)

        binding.leftFirstline.startAnimation(leftslideout)
        binding.rightSecondline.startAnimation(rightslideout)
        binding.leftSecondline.startAnimation(slowleftslideout)
        binding.rightFirstline.startAnimation(slowrightslideout)
        binding.gift2.startAnimation(rotate)
        binding.gift.startAnimation(antirotate)
        binding.star.startAnimation(fadeout)

        rotate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
//                binding.gift2.startAnimation(rotate)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        fadein.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.star.startAnimation(fadeout)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        fadeout.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.star.startAnimation(fadein)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        leftslidein.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                binding.rightSecondline.startAnimation(rightslideout)
                binding.leftFirstline.startAnimation(leftslideout)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        leftslideout.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {


                binding.rightSecondline.startAnimation(rightslidein)
                binding.leftFirstline.startAnimation(leftslidein)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
        slowleftslidein.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.leftSecondline.startAnimation(slowleftslideout)
                binding.rightFirstline.startAnimation(slowrightslideout)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
        slowleftslideout.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.leftSecondline.startAnimation(slowleftslidein)
                binding.rightFirstline.startAnimation(slowrightslidein)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

    }

    private fun GetUpcommingData(homeRequest: CommonRequest) {
        binding.bidRecyclerView.visibility = View.GONE
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
        binding.upcomingBtnText.setTextColor(const.getColor(R.color.black))
        binding.upcomingBtnText.background =
            const.getDrawable(R.drawable.white_rounded_bg)

        binding.liveBtnText.setTextColor(const.getColor(R.color.white))
        binding.liveBtn.alpha = 0.7f
        binding.liveBtnText.background = const.getDrawable(R.drawable.white_holo_bg)

        binding.closeBtnText.setTextColor(const.getColor(R.color.white))
        binding.closeBtn.alpha = 0.7f
        binding.closeBtnText.background = const.getDrawable(R.drawable.white_holo_bg)


        UpcommingBidList.clear()
        viewmodel.GetupcomingBidsList(homeRequest).observe(const) {
            if (it.status == 200) {
                UpcommingBidList = it.offers as ArrayList<HomeList>

                Log.e("Auction", "${UpcommingBidList.size}   UpcommingBidList.size")
                if (UpcommingBidList.size > 0) {
                    binding.nodatafound.visibility = View.GONE
                    binding.bidRecyclerView.visibility = View.VISIBLE
                    binding.relax.visibility = View.GONE

                    mAdapter = LiveBidProductsListAdapter(
                        UpcommingBidList,
                        const,
                        "upcomming"
                    )
                    binding.bidRecyclerView.apply {
//                        setItemAnimator(DefaultItemAnimator())
                        setAdapter(mAdapter)
                        mAdapter!!.notifyDataSetChanged()
                    }

                    binding.shimmer.stopShimmer()
                    binding.shimmer.visibility = View.GONE
                    binding.homeRefresh.isRefreshing = false

                } else {
                    binding.relax.visibility = View.GONE
                    binding.shimmer.stopShimmer()
                    binding.shimmer.visibility = View.GONE
                    Log.e("Auction", "${UpcommingBidList.size}   UpcommingBidList.size   2 ")
                    binding.nodatafound.visibility = View.VISIBLE
                    binding.animm.addAnimatorListener(object : AnimatorListener {
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

                    binding.homeRefresh.isRefreshing = false
                }
            } else {

                binding.homeRefresh.isRefreshing = false
                Log.e(tag, "GetUpcommingData apis error ${it.message}")
            }
        }
    }

    private fun GetCompletedData(homeRequest: CommonRequest) {

        binding.bidRecyclerView.visibility = View.GONE
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
        ClosedBidList.clear()

        binding.closeBtnText.setTextColor(const.getColor(R.color.black))
        binding.closeBtn.alpha = 1f
        binding.closeBtnText.background =
            const.getDrawable(R.drawable.white_rounded_bg)

        binding.upcomingBtnText.setTextColor(const.getColor(R.color.white))
        binding.upcomingBtnText.background =
            const.getDrawable(R.drawable.white_holo_bg)
        binding.upcomingBtn.alpha = 0.7f

        binding.liveBtnText.setTextColor(const.getColor(R.color.white))
        binding.liveBtn.alpha = 0.7f
        binding.liveBtnText.background = const.getDrawable(R.drawable.white_holo_bg)

        viewmodel.GetcompletedBidsList(homeRequest).observe(const) {
            binding.homeRefresh.isRefreshing = false
            if (it.status == 200) {
                ClosedBidList = it.offers as ArrayList<HomeList>
                Log.e("Auction", "${ClosedBidList.size}   ClosedBidList.size")
                if (ClosedBidList.size > 0) {
                    binding.nodatafound.visibility = View.GONE
                    binding.bidRecyclerView.visibility = View.VISIBLE
                    binding.relax.visibility = View.GONE

                    mAdapter = LiveBidProductsListAdapter(
                        ClosedBidList,
                        const,
                        "cloased"
                    )
                    binding.bidRecyclerView.apply {
//                        setItemAnimator(DefaultItemAnimator())
                        setAdapter(mAdapter)
                        mAdapter!!.notifyDataSetChanged()
                    }
                    binding.shimmer.stopShimmer()
                    binding.shimmer.visibility = View.GONE

                } else {
                    binding.relax.visibility = View.GONE
                    binding.shimmer.stopShimmer()
                    binding.shimmer.visibility = View.GONE
                    Log.e("Auction", "${ClosedBidList.size}   ClosedBidList.size  2 ")
                    binding.nodatafound.visibility = View.VISIBLE
                    binding.animm.addAnimatorListener(object : AnimatorListener {
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
                Log.e(tag, "GetUpcommingData apis error ${it.message}")
            }
        }
    }

    private fun GetLiveData(homeRequest: CommonRequest) {

        binding.bidRecyclerView.visibility = View.GONE
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE

        LiveBidList.clear()

        binding.liveBtnText.setTextColor(const.getColor(R.color.black))
        binding.liveBtnText.background =
            const.getDrawable(R.drawable.white_rounded_bg)

        binding.upcomingBtnText.setTextColor(const.getColor(R.color.white))
        binding.upcomingBtnText.background =
            const.getDrawable(R.drawable.white_holo_bg)
        binding.upcomingBtn.alpha= 0.7f

        binding.closeBtnText.setTextColor(const.getColor(R.color.white))
        binding.closeBtnText.background = const.getDrawable(R.drawable.white_holo_bg)
        binding.closeBtn.alpha= 0.7f
        viewmodel.GetLiveData(homeRequest).observe(const) {

            binding.homeRefresh.isRefreshing = false
            if (it.status == 200) {
                LiveBidList = it.offers as ArrayList<HomeList>
                Log.e("Auction", "${LiveBidList.size}   LiveBidList.size")
                if (LiveBidList.size > 0) {
                    binding.nodatafound.visibility = View.GONE
                    binding.bidRecyclerView.visibility = View.VISIBLE
                    binding.relax.visibility = View.GONE

                    mAdapter = LiveBidProductsListAdapter(
                        LiveBidList,
                        const,
                        "live"
                    )

                    binding.bidRecyclerView.apply {
//                        setItemAnimator(DefaultItemAnimator())
                        setAdapter(mAdapter)
                        mAdapter!!.notifyDataSetChanged()
                    }
                    binding.shimmer.stopShimmer()
                    binding.shimmer.visibility = View.GONE
                } else {
                    binding.shimmer.stopShimmer()
                    binding.shimmer.visibility = View.GONE
                    binding.nodatafound.visibility = View.GONE
                    Log.e("Auction", "${LiveBidList.size}     LiveBidList.size   2 ")
                    binding.bidRecyclerView.visibility = View.GONE
                    binding.relax.visibility = View.VISIBLE
                }
            } else {
                Log.e(tag, "GetUpcommingData apis error ${it.message}")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): AuctionFragment {
            return AuctionFragment()
        }
    }
    private fun handleNotificationPermission() {
        when {
            // Android 13 and above
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                requestNotificationPermission()
            }

            // Android 9 and 10 (API Level 28 and 29)
            Build.VERSION.SDK_INT in Build.VERSION_CODES.P..Build.VERSION_CODES.Q -> {
                // No additional permission required for notifications
                // You can show notifications directly
                // Permission granted, show the notification
//                Toast.makeText(const,"Notification Permission Granted!!",Toast.LENGTH_SHORT).show()
            }

            // Android versions below 9 (API Level < 28)
            Build.VERSION.SDK_INT < Build.VERSION_CODES.P -> {
                // No additional permission required for notifications
                // You can show notifications directly
                // Permission granted, show the notification
//                Toast.makeText(const,"Notification Permission Granted!!",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun requestNotificationPermission() {
        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(
                const,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted, show the notification
        } else {
            // Request the notification permission
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // Register the permission callback
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, show the notification
            Toast.makeText(const, "Notification Permission Granted!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(const, "Notification Permission Denied!!", Toast.LENGTH_SHORT).show()
        }
    }

}
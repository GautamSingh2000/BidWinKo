package com.bidwinko.screens.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.viewpager2.widget.ViewPager2
import com.bidwinko.R
import com.bidwinko.adapter.LiveBidProductsListAdapter
import com.bidwinko.components.BannerImageSlider.bannerImageAdapter
import com.bidwinko.components.BannerImageSlider.ImageItem
import com.bidwinko.databinding.FragmentHomeBinding
import com.bidwinko.model.ResponseModels.AppBanner
import com.bidwinko.model.ResponseModels.HomeResponse
import com.bidwinko.model.ResponseModels.ListData
import com.bidwinko.screens.activity.ProductDetailsActivity
import com.bidwinko.viewModel.mainViewModel

private lateinit var viewpager2: ViewPager2
private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
private val params = LinearLayout.LayoutParams(
    LinearLayout.LayoutParams.WRAP_CONTENT,
    LinearLayout.LayoutParams.WRAP_CONTENT
).apply {
    setMargins(8, 0, 8, 0)
}

class AuctionFragment : Fragment() {
    private lateinit var viewmodel: mainViewModel
    lateinit var binding: FragmentHomeBinding
    var LiveBidList = ArrayList<ListData>()
    var ClosedBidList = ArrayList<ListData>()
    var UpcommingBidList = ArrayList<ListData>()
    var banner_list = ArrayList<ImageItem>()
    private var mAdapter: LiveBidProductsListAdapter? = null
    var progressDialog: ProgressDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().findViewById<TextView>(R.id.title).setText(R.string.current_auction)
        viewmodel = mainViewModel(requireContext())
        binding = FragmentHomeBinding.inflate(layoutInflater)

        viewpager2 = binding.latestDealslider
        setAnimation()
        GetHomeData()

        binding.upcomingBtn.setOnClickListener {
            binding.upcomingBtnText.setTextColor(requireContext().getColor(R.color.black))
            binding.upcomingBtnText.setBackground(requireContext().getDrawable(R.drawable.white_rounded_bg))

            binding.liveBtnText.setTextColor(requireContext().getColor(R.color.white))
            binding.liveBtnText.setBackground(requireContext().getDrawable(R.drawable.white_holo_bg))

            binding.closeBtnText.setTextColor(requireContext().getColor(R.color.white))
            binding.closeBtnText.setBackground(requireContext().getDrawable(R.drawable.white_holo_bg))
            if (UpcommingBidList.size > 0) {
                mAdapter = LiveBidProductsListAdapter(
                    UpcommingBidList,
                    requireContext(),
                    "upcomming"
                )
                binding.recyclerView.apply {
                    setItemAnimator(DefaultItemAnimator())
                    setAdapter(mAdapter)
                }
            } else {
                Toast.makeText(requireContext(), "No Data !!", Toast.LENGTH_SHORT).show()
            }


            binding.liveBtn.setOnClickListener {

                binding.liveBtnText.setTextColor(requireContext().getColor(R.color.black))
                binding.liveBtnText.setBackground(requireContext().getDrawable(R.drawable.white_rounded_bg))

                binding.upcomingBtnText.setTextColor(requireContext().getColor(R.color.white))
                binding.upcomingBtnText.setBackground(requireContext().getDrawable(R.drawable.white_holo_bg))

                binding.closeBtnText.setTextColor(requireContext().getColor(R.color.white))
                binding.closeBtnText.setBackground(requireContext().getDrawable(R.drawable.white_holo_bg))

                if (LiveBidList.size > 0) {
                    mAdapter = LiveBidProductsListAdapter(
                        LiveBidList,
                        requireContext(),
                        "live"
                    )
                    binding.recyclerView.apply {
                        setItemAnimator(DefaultItemAnimator())
                        setAdapter(mAdapter)
                    }

                } else {
                    Toast.makeText(requireContext(), "No Data !!", Toast.LENGTH_SHORT).show()
                }
            }

            binding.closeBtn.setOnClickListener {

                binding.closeBtnText.setTextColor(requireContext().getColor(R.color.black))
                binding.closeBtnText.setBackground(requireContext().getDrawable(R.drawable.white_rounded_bg))

                binding.upcomingBtnText.setTextColor(requireContext().getColor(R.color.white))
                binding.upcomingBtnText.setBackground(requireContext().getDrawable(R.drawable.white_holo_bg))

                binding.liveBtnText.setTextColor(requireContext().getColor(R.color.white))
                binding.liveBtnText.setBackground(requireContext().getDrawable(R.drawable.white_holo_bg))

                if (ClosedBidList.size > 0) {
                    mAdapter = LiveBidProductsListAdapter(
                        ClosedBidList,
                        requireContext(),
                        "cloased"
                    )
                    binding.recyclerView.apply {
                        setItemAnimator(DefaultItemAnimator())
                        setAdapter(mAdapter)
                    }

                } else {
                    Toast.makeText(requireContext(), "No Data !!", Toast.LENGTH_SHORT).show()
                }
            }

            binding.howToBidBtn.let {
                val textView = binding.howToBidBtnText
                it.setOnClickListener {
                    if (binding.biddingsteps.visibility == View.VISIBLE) {
                        textView.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, ContextCompat.getDrawable(
                                requireContext(), R.drawable.down_btn
                            ), null
                        )
                        binding.biddingsteps.visibility = View.GONE
                    } else {
                        textView.setCompoundDrawablesWithIntrinsicBounds(
                            null, null, ContextCompat.getDrawable(
                                requireContext(), R.drawable.up
                            ), null
                        )
                        binding.biddingsteps.visibility = View.VISIBLE
                    }
                }
            }
        }
        return binding.root
    }

    private fun GetHomeData() {
        viewmodel.GetHomeData().observe(requireActivity()) {
            if (it.status == 200) {
                setBannerData(it.appBanners)
                getAllList(it)
            } else {
                Log.e(tag, "banner apis error ${it.message}")
            }

        }
    }

    private fun setBannerData(bannerlist: List<AppBanner>) {
        for (banner in bannerlist) {
            banner_list.add(ImageItem(banner.id.toString(), banner.image))
        }
        val imageAdapter = bannerImageAdapter()
        viewpager2.adapter = imageAdapter
        imageAdapter.submitList(banner_list)

        val dotsImage = Array(banner_list.size) { ImageView(requireContext()) }

        dotsImage.forEach {
            it.setImageResource(
                R.drawable.dot
            )
            binding.slideDotLL.addView(it, params)
        }

        // default first dot selected
        dotsImage[0].setImageResource(R.drawable.dot)

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

    }


    private fun setAnimation(){

        val leftslidein  = AnimationUtils.loadAnimation(requireContext(), R.anim.left_slide_in)
        val leftslideout  = AnimationUtils.loadAnimation(requireContext(), R.anim.left_slide_out)
        val rightslidein  = AnimationUtils.loadAnimation(requireContext(), R.anim.right_slide_in)
        val rightslideout  = AnimationUtils.loadAnimation(requireContext(), R.anim.right_slide_out)

        val slowleftslidein  = AnimationUtils.loadAnimation(requireContext(), R.anim.slowleftin)
        val slowleftslideout  = AnimationUtils.loadAnimation(requireContext(), R.anim.slowleftout)
        val slowrightslidein  = AnimationUtils.loadAnimation(requireContext(), R.anim.slowrightin)
        val slowrightslideout  = AnimationUtils.loadAnimation(requireContext(), R.anim.slowrightout)

        val fadein  = AnimationUtils.loadAnimation(requireContext(), R.anim.fadein)
        val fadeout  = AnimationUtils.loadAnimation(requireContext(), R.anim.fadeout)
        val rotate  = AnimationUtils.loadAnimation(requireContext(), R.anim.clockwise_rotate)
        val antirotate  = AnimationUtils.loadAnimation(requireContext(), R.anim.aniclock_rotate)

        binding.leftFirstline.startAnimation(leftslideout)
        binding.rightSecondline.startAnimation(rightslideout)
        binding.leftSecondline.startAnimation(slowleftslideout)
        binding.rightFirstline.startAnimation(slowrightslideout)
        binding.gift2.startAnimation(rotate)
        binding.gift.startAnimation(antirotate)
        binding.star.startAnimation(fadeout)

        rotate.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
//                binding.gift2.startAnimation(rotate)
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        fadein.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                binding.star.startAnimation(fadeout)
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        fadeout.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                binding.star.startAnimation(fadein)
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        leftslidein.setAnimationListener(object : Animation.AnimationListener{

            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {

                binding.rightSecondline.startAnimation(rightslideout)
                binding.leftFirstline.startAnimation(leftslideout)
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        leftslideout.setAnimationListener(object : Animation.AnimationListener{

            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {


                binding.rightSecondline.startAnimation(rightslidein)
                binding.leftFirstline.startAnimation(leftslidein)
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
        slowleftslidein.setAnimationListener(object : Animation.AnimationListener{

            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                binding.leftSecondline.startAnimation(slowleftslideout)
                binding.rightFirstline.startAnimation(slowrightslideout)
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
        slowleftslideout.setAnimationListener(object : Animation.AnimationListener{

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
    private fun getAllList(data: HomeResponse) {
        LiveBidList.clear()
        LiveBidList = data.liveOffers as ArrayList<ListData>
        ClosedBidList.clear()
        ClosedBidList = data.completedOffers as ArrayList<ListData>
        UpcommingBidList.clear()
        UpcommingBidList = data.upcomingOffers as ArrayList<ListData>
        mAdapter =
            LiveBidProductsListAdapter(
                LiveBidList,
                requireContext(),
                "live"
            )
        binding.recyclerView.apply {
            setItemAnimator(DefaultItemAnimator())
            setAdapter(mAdapter)
        }
        mAdapter!!.setOnItemClickListener { position, v ->
            val intentProductDetails =
                Intent(activity, ProductDetailsActivity::class.java)
            intentProductDetails.putExtra(
                "bidofferId",
                LiveBidList[position].id
            )
            startActivity(intentProductDetails)
        }
    }
    private fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    override fun onDestroy() {
        dismissProgressDialog()
        super.onDestroy()
    }

    override fun onPause() {
        dismissProgressDialog()
        super.onPause()
    }

    companion object {
        @JvmStatic
        fun newInstance(): AuctionFragment {
            return AuctionFragment()
        }
    }
}
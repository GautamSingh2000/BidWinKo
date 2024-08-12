package com.bidwinko.adapter

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.bidwinko.R
import com.bidwinko.components.Countdown.CountDown
import com.bidwinko.model.ResponseModels.HomeList
import com.bidwinko.screens.activity.ClosedBidWinnerActivity
import com.bidwinko.screens.activity.ProductDetailsActivity
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LiveBidProductsListAdapter(
    private val bidofferArrayList: ArrayList<HomeList>,
    var context: Context,
    var type: String,
) : RecyclerView.Adapter<LiveBidProductsListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardView: CardView
        var proName: TextView
        var ribbon_tag: TextView
        var bitbtn: TextView
        var Timer: TextView
        var proImage: ImageView
        var ribbon: ImageView
        var bid_btn: CardView
        var liveanim: LottieAnimationView

        val countDown = CountDown()

        init {
            cardView = view.findViewById(R.id.carview)
            proName = view.findViewById<TextView>(R.id.productname)!!
            liveanim = view.findViewById(R.id.live_anim)
            Timer = view.findViewById<View>(R.id.transactiontime) as TextView
            proImage = view.findViewById(R.id.productimage)
            ribbon = view.findViewById(R.id.ribbon)
            bid_btn = view.findViewById(R.id.bid_button)
            bitbtn = view.findViewById(R.id.bid_buttontext)
            ribbon_tag = view.findViewById(R.id.ribbon_tag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_live_bid_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = bidofferArrayList[position]

        when (type) {
            "cloased" -> {
                holder.liveanim.setAnimation(R.raw.gray_dot)
                holder.ribbon.imageTintList = context.getColorStateList(R.color.gray)
                holder.ribbon_tag.text = "Closed"
                val date = Date(data.endDate * 1000)
                val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = format.format(date)
                holder.Timer.text = formattedDate
                holder.bitbtn.text = "Rs ${data.offerPrice}"
                holder.Timer.setTextColor(context.getColor(R.color.gray))
                holder.countDown.stopCountDownTimer()

            }

            "upcomming" -> {
                val leftPadding = convertDpToPx(2f)
                val topPadding = convertDpToPx(11f)
                val rightPadding = convertDpToPx(0f)
                val bottomPadding = convertDpToPx(11f)
                holder.liveanim.setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
                holder.bitbtn.background = context.getDrawable(R.drawable.simple_gray_box)
                holder.liveanim.setAnimation(R.raw.clock)
                holder.ribbon.imageTintList = context.getColorStateList(R.color.yellow)
                holder.ribbon_tag.visibility = View.GONE
                val date = Date(data.startDate * 100)
                val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = format.format(date)
                holder.Timer.text = formattedDate
                holder.Timer.setTextColor(context.getColor(R.color.yellow))
                holder.countDown.stopCountDownTimer()
            }
        }
        holder.proName.text = data.offerName
        Glide.with(context)
            .load(data.offerImage)
            .into(holder.proImage)
        holder.liveanim.repeatMode = LottieDrawable.RESTART
        holder.liveanim.addAnimatorListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                holder.liveanim.playAnimation()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        var intentProductDetails: Intent
        if (type.equals("cloased")) {
            intentProductDetails = Intent(context, ClosedBidWinnerActivity::class.java)
        } else {
            intentProductDetails = Intent(context, ProductDetailsActivity::class.java)
        }

        holder.bid_btn.setOnClickListener {
            intentProductDetails.putExtra("bidofferId", data.id)
            intentProductDetails.putExtra("type", type)
            Log.e("adapter", "${data.id} id")
            context.startActivity(intentProductDetails)
//            (context as Activity).overridePendingTransition(R.anim.fast_left_slide_out, R.anim.fast_right_slide_in)
        }

        holder.cardView.setOnClickListener {
            Log.e("adapter", "${data.id} id")
            intentProductDetails.putExtra("type", type)
            intentProductDetails.putExtra("bidofferId", data.id)
            context.startActivity(intentProductDetails)
//            (context as Activity).overridePendingTransition(R.anim.fast_left_slide_out, R.anim.fast_right_slide_in)

        }
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.liveanim.pauseAnimation()
        holder.countDown.stopCountDownTimer()
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.liveanim.playAnimation()
        val bidoffer = bidofferArrayList[holder.adapterPosition]
        if (type == "live") {
            val inputDateString = bidoffer.endDate
            val startingdate = bidoffer.startDate
            holder.countDown.start(startingdate, inputDateString, holder.Timer, null)
        }
    }

    override fun getItemCount(): Int {
        return bidofferArrayList.size
    }

//    companion object {
//        var clickListener: ClickListener? = null
//    }

    private fun convertDpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}
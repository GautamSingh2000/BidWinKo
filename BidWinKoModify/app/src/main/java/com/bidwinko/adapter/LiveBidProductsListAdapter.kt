package com.bidwinko.adapter

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
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
import com.bidwinko.components.CountDown
import com.bidwinko.model.ResponseModels.ListData
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer

class LiveBidProductsListAdapter(
    private val bidofferArrayList: ArrayList<ListData>,
    var context: Context,
    var type: String,
) : RecyclerView.Adapter<LiveBidProductsListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var proName: TextView
        var ribbon_tag: TextView
        var bitbtn: TextView
        var Timer: TextView
        var proImage: ImageView
        var ribbon: ImageView
        var card_main: CardView
        var liveanim: LottieAnimationView

        val countDown = CountDown()

        init {
            itemView.setOnClickListener(this)
            proName = view.findViewById<TextView>(R.id.productname)!!
            liveanim = view.findViewById(R.id.live_anim)
            Timer = view.findViewById<View>(R.id.time) as TextView
            proImage = view.findViewById(R.id.productimage)
            ribbon = view.findViewById(R.id.ribbon)
            card_main = view.findViewById(R.id.bid_button)
            bitbtn = view.findViewById(R.id.bid_buttontext)
            ribbon_tag = view.findViewById(R.id.ribbon_tag)
        }

        override fun onClick(view: View) {
            clickListener!!.onItemClick(adapterPosition, view)
        }
    }

    fun setOnItemClickListener(clickListener: ClickListener?) {
        Companion.clickListener = clickListener
    }

    fun interface ClickListener {
        fun onItemClick(position: Int, v: View?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.product_list_item, parent, false)
            .inflate(R.layout.single_live_bid_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bidoffer = bidofferArrayList[position]

        when (type) {
            "cloased" -> {
                holder.liveanim.setAnimation(R.raw.gray_dot)
                holder.ribbon.imageTintList = context.getColorStateList(R.color.gray)
                holder.ribbon_tag.text = "Closed"
                val date = Date(bidoffer.endDate * 1000)
                val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = format.format(date)
                holder.Timer.text = formattedDate
                holder.bitbtn.text = "Rs ${bidoffer.offerPrice}"
                holder.Timer.setTextColor(context.getColor(R.color.gray))
                holder.countDown.stopCountDownTimer()

            }

            "upcomming" -> {
                val leftPadding = convertDpToPx(2f)
                val topPadding = convertDpToPx(11f)
                val rightPadding = convertDpToPx(0f)
                val bottomPadding = convertDpToPx(11f)
                holder.liveanim.setPadding(leftPadding, topPadding, rightPadding, bottomPadding)

                holder.liveanim.setAnimation(R.raw.clock)
                holder.ribbon.imageTintList = context.getColorStateList(R.color.yellow)
                holder.ribbon_tag.visibility = View.GONE
                val date = Date(bidoffer.startDate * 100)
                val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = format.format(date)
                holder.Timer.text = formattedDate
                holder.Timer.setTextColor(context.getColor(R.color.yellow))
                holder.countDown.stopCountDownTimer()
            }
        }
        holder.proName.text = bidoffer.offerName
        Glide.with(context)
            .load(bidoffer.offerImage)
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

//        holder.card_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentProductDetails = new Intent(context, ProductDetailsActivity.class);
//                intentProductDetails.putExtra("bidofferId", bidofferArrayList.get(position).getBidofferId());
//                context.startActivity(intentProductDetails);
//            }
//        });
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
            holder.countDown.start(inputDateString, holder.Timer)
        }
    }

    override fun getItemCount(): Int {
        return bidofferArrayList.size
    }

    companion object {
        var clickListener: ClickListener? = null
    }

    private fun convertDpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}
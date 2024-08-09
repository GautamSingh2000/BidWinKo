package com.bidwinko.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bidwinko.R
import com.bidwinko.model.RequestModels.PaymentRequest
import com.bidwinko.model.ResponseModels.Plan
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel


class BuyBidListAdapter(
    val plans: ArrayList<Plan>,
    var context: Context,
) : RecyclerView.Adapter<BuyBidListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var no_of_bids: TextView = view.findViewById(R.id.no_of_bid)
        var expiry: TextView = view.findViewById(R.id.expiry_tv)
        var offer_text: TextView = view.findViewById(R.id.offer_text)
        var bidAmount: TextView = view.findViewById(R.id.amount_tv)
        var card_main: CardView = view.findViewById(R.id.carview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_buy_bid_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val buyBidValue = plans[position]
        holder.no_of_bids.text = "${buyBidValue.noOfBids} Bids"
        holder.expiry.text = "Expire in ${buyBidValue.expiresIn}"
        holder.offer_text.text = "${buyBidValue.offPercentage}% OFF"
        holder.bidAmount.text = "${buyBidValue.planPrice}"

        holder.card_main.setOnClickListener {

            val request = PaymentRequest(
                planId =buyBidValue.planId.toString(),
                userId = SessionManager(context).GetValue(Constants.USER_ID).toString(),
                securityToken = SessionManager(context).GetValue(Constants.SECURITY_TOKEN),
                versionName = SessionManager(context).GetValue(Constants.VERSION_NAME),
                versionCode = SessionManager(context).GetValue(Constants.VERSION_CODE)
            )
            mainViewModel(context).GetPayment(request).observe(context as LifecycleOwner)
            {
                if (it.status == 200)
                {
                  openTab(it.url)
                }else{
                    Toast.makeText(context, "Not Able To Open !!", Toast.LENGTH_SHORT).show()
                }
            }
        }
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

    override fun getItemCount(): Int {
        return plans.size
    }
}
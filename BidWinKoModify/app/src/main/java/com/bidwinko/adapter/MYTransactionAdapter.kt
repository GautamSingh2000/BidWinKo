package com.bidwinko.adapter

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bidwinko.R
import com.bidwinko.model.RequestModels.PaymentRequest
import com.bidwinko.model.ResponseModels.Transaction
import com.bidwinko.model.ResponseModels.TransactionHistoryResponse
import com.bidwinko.utilies.Constants
import com.bidwinko.utilies.SessionManager
import com.bidwinko.viewModel.mainViewModel

class MYTransactionAdapter(val list : ArrayList<Transaction>, var context: Context) :
    RecyclerView.Adapter<MYTransactionAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_transaction_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = list.get(position)
        holder.srno.text = items.serialNo
        holder.transName.text = items.title
        holder.transamount.text = "${items.amount}"
        holder.bids.text = "${items.bids} Bids"
        holder.transactiondate.text = items.date
        holder.transactiontime.text = items.time
        holder.anim.playAnimation()
        holder.anim.let {
            it.playAnimation()
            it.addAnimatorListener(object  : AnimatorListener{
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
    }

    override fun getItemCount(): Int {
        return if (list != null) {
            list.size
        } else 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var srno: TextView
        var transName: TextView
        var transamount: TextView
        var transactiondate: TextView
        var transactiontime: TextView
        var bids: TextView
        var image: ImageView
        var anim : LottieAnimationView

        init {
            srno = itemView.findViewById(R.id.srno)
            transName = itemView.findViewById(R.id.transName)
            transamount = itemView.findViewById(R.id.transamount)
            transactiondate = itemView.findViewById(R.id.transactiondate)
            transactiontime = itemView.findViewById(R.id.transactiontime)
            bids = itemView.findViewById(R.id.bids)
            image = itemView.findViewById(R.id.userimage)
            anim = itemView.findViewById(R.id.anim)

        }
    }
}

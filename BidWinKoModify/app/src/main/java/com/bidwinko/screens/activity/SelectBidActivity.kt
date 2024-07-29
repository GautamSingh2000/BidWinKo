package com.bidwinko.screens.activity

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.layout.Layout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bidwinko.R
import com.bidwinko.adapter.LoweRangeBidAdapter
import com.bidwinko.adapter.UpperRangeBidAdapter
import com.bidwinko.databinding.ActivitySelectBidBinding

class SelectBidActivity : AppCompatActivity(), UpperRangeBidAdapter.UpperBidRangeitemClickListener,
    LoweRangeBidAdapter.LowerBidRangeitemClickListener {
    val upperlist = ArrayList<String>()
    val lowerlist = ArrayList<String>()
    var bidsAvailable = 5
    var upperRvPosition = 0
    val lowerSelectedBidList = ArrayList<String>()
    private lateinit var lowerListAdapter: LoweRangeBidAdapter
    private lateinit var binding: ActivitySelectBidBinding
    var itemclick = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBidBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bidsAvailable.text =  "$bidsAvailable Bids"

        getInitialUpperRange()
        getInitialLoweRange()

        binding.rangerv.adapter = UpperRangeBidAdapter(this, upperlist, this)

        lowerListAdapter = LoweRangeBidAdapter(this, lowerlist, this)
        binding.allbidRv.adapter = lowerListAdapter

//        binding.nextbtn.setOnClickListener {
//            val position = upperRvPosition + 8
//            binding.rangerv.smoothScrollToPosition(position )
//        }
//        binding.prevbtn.setOnClickListener {
//            val position = upperRvPosition - 8
//            binding.rangerv.smoothScrollToPosition(position )
//        }

        binding.resetBtn.setOnClickListener {
            val anim = AnimationUtils.loadAnimation(this,R.anim.fast_rotate_clock_wise)
            binding.resetBtn.startAnimation(anim)
            bidsAvailable = 5
            binding.bidsAvailable.text = "$bidsAvailable Bid"
            lowerSelectedBidList.clear()
            lowerListAdapter.notifyDataSetChanged()
            binding.rangerv.smoothScrollToPosition(0)

            binding.placeBid.backgroundTintList = ContextCompat.getColorStateList(this, R.color.littledarkgray)
            binding.placeBid.setTextColor(ContextCompat.getColor(this,R.color.white))
        }

    }

    private fun getInitialUpperRange()
    {
        for (i in 0..100) {
            upperlist.add(i.toString())
        }
    }
    private fun getInitialLoweRange() {
        for (i in 0..99) {
            val number = i / 100.0
            lowerlist.add(number.toString())
        }
    }

    fun generateValues(inputNumber: Int): ArrayList<String> {
        val numbers = ArrayList<String>()

        for (i in inputNumber * 100..inputNumber * 100 + 99) {
            val number = i / 100.0
            numbers.add(number.toString())
        }
        return numbers
    }

    override fun onLowerBidRangeItemClick(item: String) {
        if(bidsAvailable>0) {
            val found = lowerSelectedBidList.contains(item)
            if (!found) {
                lowerSelectedBidList.add(item)
                bidsAvailable--
                binding.bidsAvailable.text =  "$bidsAvailable Bids"
                binding.placeBid.let {
                    it.backgroundTintList =
                        ContextCompat.getColorStateList(this, R.color.lightgreen)
                    it.setTextColor(ContextCompat.getColor(this, R.color.green))
                }

            } else {
                lowerSelectedBidList.remove(item)
                bidsAvailable++
                binding.bidsAvailable.text = "$bidsAvailable Bids"
                if (lowerSelectedBidList.size == 0) {
                    binding.placeBid.backgroundTintList =
                        ContextCompat.getColorStateList(this, R.color.littledarkgray)
                    binding.placeBid.setTextColor(ContextCompat.getColor(this, R.color.white))
                }
            }
        }else{
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(500)
            Toast.makeText(this,"No more bids are available!!",Toast.LENGTH_LONG).show()
        }
        Log.e("SelectBidActivity","size of lowerSelectedBidList is ${lowerSelectedBidList.size}")
    }

    override fun onUpperBidRangeItemClick(item: String, position: Int) {
        itemclick = item
        upperRvPosition = position
        val numbers = generateValues(itemclick.toInt())
        lowerListAdapter.updateList(numbers,lowerSelectedBidList)
        lowerListAdapter.notifyDataSetChanged()
    }

}
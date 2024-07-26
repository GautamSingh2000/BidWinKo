package com.bidwinko.components

import android.os.CountDownTimer
import android.widget.TextView
import java.util.Date

class CountDown() {
    fun start(inputDateString: Long, textView: TextView) {
        try {
            val currentDate = Date()
            val inputDate = Date(inputDateString * 1000) // Convert seconds to milliseconds
            val diffInMillis = inputDate.time - currentDate.time

            if (diffInMillis > 0) {
                startCountDownTimer(diffInMillis, textView)
            } else {
                textView.text = "Date is in the past"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            textView.text = "Error parsing date"
        }
    }


    var countDownTimer: CountDownTimer? = null
    private fun startCountDownTimer(diffInMillis: Long, textView: TextView) {
        countDownTimer?.cancel() // Cancel any existing timer

        countDownTimer = object : CountDownTimer(diffInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val days = millisUntilFinished / (1000 * 60 * 60 * 24)
                val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24
                val minutes = (millisUntilFinished / (1000 * 60)) % 60
                val seconds = (millisUntilFinished / 1000) % 60

                val parts = mutableListOf<String>()
                if (days > 0) parts.add("${days}d")
                if (hours > 0 || days > 0) parts.add("${hours}h")
                if (minutes > 0 || hours > 0 || days > 0) parts.add("${minutes}m")
                if (seconds > 0 || minutes > 0 || hours > 0 || days > 0) parts.add("${seconds}s")

                val countdown = parts.joinToString(":")
                textView.text = countdown
            }

            override fun onFinish() {
                textView.text = "0s"
            }
        }.start()
    }

    fun stopCountDownTimer() {
        countDownTimer?.cancel()
    }
}
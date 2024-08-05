package com.bidwinko.components

import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView
import java.util.Date

class CountDown {
    private var countDownTimer: CountDownTimer? = null

    fun start(startTime: Long , endTime: Long, textView: TextView, seekBar: com.bidwinko.components.CustomSeekBar?) {
        try {
            val startDate = Date(startTime * 1000) // Convert seconds to milliseconds
            val endDate = Date(endTime * 1000)
            val currentDate = Date()

            val totalTimeInMillis = endDate.time - startDate.time
            val remainingTimeInMillis = endDate.time - currentDate.time

            if (remainingTimeInMillis > 0) {
                seekBar?.max = (totalTimeInMillis / 1000).toInt() // Set SeekBar max to total seconds
                startCountDownTimer(remainingTimeInMillis, totalTimeInMillis, textView, seekBar)
            } else {
                textView.text = "Time is up"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            textView.text = "Error parsing date"
            if (seekBar != null) {
                seekBar.progress = 0
            }
        }
    }

    private fun startCountDownTimer(remainingTimeInMillis: Long, totalTimeInMillis: Long, textView: TextView, seekBar: SeekBar?) {
        countDownTimer?.cancel() // Cancel any existing timer

        countDownTimer = object : CountDownTimer(remainingTimeInMillis, 1000) {
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

                // Update SeekBar progress
                if(seekBar != null) {
                    val elapsedTimeInMillis = totalTimeInMillis - millisUntilFinished
                    val progress =
                        ((elapsedTimeInMillis / totalTimeInMillis.toFloat()) * seekBar.max).toInt()
                    seekBar?.progress = progress
                }
            }

            override fun onFinish() {
                textView.text = "Time is up"
                if (seekBar != null) {
                    seekBar.progress = seekBar.max
                }
            }
        }.start()
    }

    fun stopCountDownTimer() {
        countDownTimer?.cancel()
    }
}

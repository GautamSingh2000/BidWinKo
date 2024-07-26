package com.bidwinko.utilies

import android.content.Context
import android.util.TypedValue

fun convertDpToPx(dp: Float, context : Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    ).toInt()
}
package com.anehta.camela.utils

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

@BindingAdapter("app:ratioToText")
fun setRatioToText(view: TextView, ratio: ScreenUtil.Ratio?) {
    ratio?.let {
        Log.d("BindingAdapter", "Ratio: $ratio")
        view.text = when (it) {
            ScreenUtil.Ratio.Ratio_1_1 -> "1:1"
            ScreenUtil.Ratio.Ratio_3_4 -> "3:4"
            ScreenUtil.Ratio.Ratio_9_16 -> "9:16"
            ScreenUtil.Ratio.Ratio_Full -> "Full"
        }
    }
}



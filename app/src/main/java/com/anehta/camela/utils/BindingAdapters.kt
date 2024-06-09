package com.anehta.camela.utils

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter

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

@BindingAdapter("app:zoomToText")
fun setZoomToText(view: TextView, zoom: ScreenUtil.Zoom?) {
    zoom?.let {
        Log.d("BindingAdapter", "Zoom: $zoom")
        view.text = when (it) {
            ScreenUtil.Zoom.Zoom_0_5x -> "0.5x"
            ScreenUtil.Zoom.Zoom_1x -> "1x"
            ScreenUtil.Zoom.Zoom_2x -> "2x"
        }
    }
}

@BindingAdapter("app:timerToText")
fun setTimerToText(view: TextView, timer: ScreenUtil.Timer?) {
    timer?.let {
        Log.d("BindingAdapter", "Timer: ${timer}")
        view.text = when (it) {
            ScreenUtil.Timer.Timer_0 -> "0s"
            ScreenUtil.Timer.Timer_3 -> "3s"
            ScreenUtil.Timer.Timer_5 -> "5s"
        }
    }
}


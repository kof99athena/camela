package com.anehta.camela.utils

import android.media.Image
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.anehta.camela.R

@BindingAdapter("app:ratioToText")
fun setRatioToText(view: ImageView, ratio: ScreenUtil.Ratio?) {
    ratio?.let {
        Log.d("BindingAdapter", "Ratio: $ratio")
        view.setImageResource(
            when (it) {
                ScreenUtil.Ratio.Ratio_1_1 -> R.drawable.ic_11
                ScreenUtil.Ratio.Ratio_3_4 -> R.drawable.ic_34
                ScreenUtil.Ratio.Ratio_9_16 -> R.drawable.ic_916
                ScreenUtil.Ratio.Ratio_Full -> R.drawable.ic_full
            }
        )
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


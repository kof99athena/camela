package com.anehta.camela.utils

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import androidx.window.layout.WindowMetricsCalculator

object ScreenUtil {
    enum class Ratio(val ratioString: String) {
        Ratio_1_1("1:1"),
        Ratio_3_4("3:4"),
        Ratio_9_16("9:16"),
        Ratio_Full("0:0");
    }

    enum class Zoom {
        Zoom_0_5x,
        Zoom_1x,
        Zoom_2x;
    }

    enum class Timer {
        Timer_0,
        Timer_3,
        Timer_5;
    }

    fun computeWindowSizeClasses(context: Context): Pair<Int, Int> {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        return Pair(width, height)
    }

    fun getViewSize(view: View?, callback: (Int?, Int?) -> Unit) {
        view?.viewTreeObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val width = view.width
                val height = view.height
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                callback.invoke(width, height)
            }
        })
    }
}
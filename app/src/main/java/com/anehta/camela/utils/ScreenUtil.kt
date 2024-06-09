package com.anehta.camela.utils

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import androidx.window.layout.WindowMetricsCalculator

object ScreenUtil {
    enum class PreviewRatio {
        Ratio_1_1,
        Ratio_3_4,
        Ratio_9_16,
        Ratio_Full;
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
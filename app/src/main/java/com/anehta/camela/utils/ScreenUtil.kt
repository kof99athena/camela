package com.anehta.camela.utils

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import androidx.window.layout.WindowMetricsCalculator

object ScreenUtil {
    enum class PreviewRatio(val width: Int, val height: Int) {
        RATIO_1_1(1, 1),
        RATIO_3_4(3, 4),
        RATIO_9_16(9, 16),
        FULL_SIZE(-1, -1);

        override fun toString(): String {
            return if (this == FULL_SIZE) "Full Size" else "$width:$height"
        }
    }

    fun getPreviewRatio(ratio: PreviewRatio): String {
        return when (ratio) {
            PreviewRatio.RATIO_1_1 -> "1:1"
            PreviewRatio.RATIO_3_4 -> "3:4"
            PreviewRatio.RATIO_9_16 -> "9:16"
            PreviewRatio.FULL_SIZE -> "Full"
        }
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
package com.anehta.camela.feature.preview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anehta.camela.feature.preview.interactors.PreviewInteractor
import com.anehta.camela.models.requests.PermissionRequest
import com.anehta.camela.utils.ScreenUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(private val interactor: PreviewInteractor) :
    ViewModel() {
    private var currentIndex = 0

    private val requestMutableLiveData = MutableLiveData<PermissionRequest>()
    val permissionRequest: LiveData<PermissionRequest>
        get() = requestMutableLiveData

    private val ratioMutableLiveData = MutableLiveData<ScreenUtil.Ratio>()
    val ratio: LiveData<ScreenUtil.Ratio>
        get() = ratioMutableLiveData
    private val ratios = ScreenUtil.Ratio.entries.toTypedArray()

    private val zoomMutableLiveData = MutableLiveData<ScreenUtil.Zoom>()
    val zoom: LiveData<ScreenUtil.Zoom>
        get() = zoomMutableLiveData
    private val zooms = ScreenUtil.Zoom.entries.toTypedArray()

    private val timerMutableLiveData = MutableLiveData<ScreenUtil.Timer>()
    val timer: LiveData<ScreenUtil.Timer>
        get() = timerMutableLiveData
    private val timers = ScreenUtil.Timer.entries.toTypedArray()

    init {
        requestMutableLiveData.value = PermissionRequest(false)
        ratioMutableLiveData.value = ratios[currentIndex]
        zoomMutableLiveData.value = zooms[currentIndex]
        timerMutableLiveData.value = timers[currentIndex]
    }

    fun getPermissionStatus() {
        val permissionStatus = interactor.getPermissionStatus()
        requestMutableLiveData.value = permissionStatus
    }

    fun setPermissionStatus(isGranted: Boolean) {
        requestMutableLiveData.value = PermissionRequest(isGranted)
    }

    fun setPreviewRatio() {
        currentIndex = (currentIndex + 1) % ratios.size
        ratioMutableLiveData.value = ratios[currentIndex]
    }

    fun setPreviewZoom() {
        currentIndex = (currentIndex + 1) % zooms.size
        zoomMutableLiveData.value = zooms[currentIndex]
    }

    fun setPreviewTimer() {
        currentIndex = (currentIndex + 1) % timers.size
        timerMutableLiveData.value = timers[currentIndex]
    }
}
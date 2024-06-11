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

    private val _permission_request = MutableLiveData<PermissionRequest>()
    val permissionRequest: LiveData<PermissionRequest>
        get() = _permission_request

    private val _preview_ratio = MutableLiveData<ScreenUtil.Ratio>()
    val ratio: LiveData<ScreenUtil.Ratio>
        get() = _preview_ratio
    private val ratios = ScreenUtil.Ratio.values()

    private val _preview_zoom = MutableLiveData<ScreenUtil.Zoom>()
    val zoom: LiveData<ScreenUtil.Zoom>
        get() = _preview_zoom
    private val zooms = ScreenUtil.Zoom.values()

    private val _preview_timer = MutableLiveData<ScreenUtil.Timer>()
    val timer: LiveData<ScreenUtil.Timer>
        get() = _preview_timer
    private val timers = ScreenUtil.Timer.values()

    init {
        _permission_request.value = PermissionRequest(false)
        _preview_ratio.value = ratios[currentIndex]
        _preview_zoom.value = zooms[currentIndex]
        _preview_timer.value = timers[currentIndex]
    }

//    fun getPermissionStatus() {
//        val permissionStatus = interactor.getPermissionStatus()
//        _permission_request.value = permissionStatus
//    }

    fun setPermissionStatus(isGranted: Boolean) {
        _permission_request.value = PermissionRequest(isGranted)
    }

    fun setPreviewRatio() {
        currentIndex = (currentIndex + 1) % ratios.size
        _preview_ratio.value = ratios[currentIndex]
    }

    fun setPreviewZoom() {
        currentIndex = (currentIndex + 1) % zooms.size
        _preview_zoom.value = zooms[currentIndex]
    }

    fun setPreviewTimer() {
        currentIndex = (currentIndex + 1) % timers.size
        _preview_timer.value = timers[currentIndex]
    }
}
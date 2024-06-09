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

    private val _permission_request = MutableLiveData<PermissionRequest>()
    val permissionRequest: LiveData<PermissionRequest>
        get() = _permission_request

    private val _preview_ratio = MutableLiveData<ScreenUtil.Ratio>()
    val ratio: LiveData<ScreenUtil.Ratio>
        get() = _preview_ratio

    private val _preview_zoom = MutableLiveData<ScreenUtil.Zoom>()
    val zoom: LiveData<ScreenUtil.Zoom>
        get() = _preview_zoom

    private val _preview_timer = MutableLiveData<ScreenUtil.Timer>()
    val timer: LiveData<ScreenUtil.Timer>
        get() = _preview_timer

    init {
        _permission_request.value = PermissionRequest(false)
        _preview_ratio.value = ScreenUtil.Ratio.Ratio_Full
        _preview_zoom.value = ScreenUtil.Zoom.Zoom_1x
        _preview_timer.value = ScreenUtil.Timer.Timer_0
    }

    fun getPermissionStatus() {
        val permissionStatus = interactor.getPermissionStatus()
        _permission_request.value = permissionStatus
    }

    fun setPermissionStatus(isGranted: Boolean) {
        _permission_request.value = PermissionRequest(isGranted)
    }

    fun setPreviewRatio(ratio: ScreenUtil.Ratio) {
        _preview_ratio.value = ratio
    }

    fun setPreviewZoom(zoom: ScreenUtil.Zoom) {
        _preview_zoom.value = zoom
    }

    fun setPreviewTimer(timer: ScreenUtil.Timer) {
        _preview_timer.value = timer
    }
}
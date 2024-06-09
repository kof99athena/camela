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

    init {
        _permission_request.value = PermissionRequest(false)
        _preview_ratio.value = ScreenUtil.Ratio.Ratio_Full
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
}
package com.anehta.camela.feature.preview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anehta.camela.feature.preview.interactors.PreviewInteractor
import com.anehta.camela.models.requests.PermissionRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(private val interactor: PreviewInteractor) :
    ViewModel() {

    private val _permission_request = MutableLiveData<PermissionRequest>()
    val permissionRequest: LiveData<PermissionRequest>
        get() = _permission_request

    init {
        _permission_request.value = PermissionRequest(false)
    }

    fun getPermissionStatus() {
        val permissionStatus = interactor.getPermissionStatus()
        _permission_request.value = permissionStatus
    }

    fun setPermissionStatus(isGranted: Boolean) {
        _permission_request.value = PermissionRequest(isGranted)
    }
}
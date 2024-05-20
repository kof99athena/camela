package com.anehta.camela.feature.preview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PreviewViewModel : ViewModel() {
    val permissionRequest = Boolean

    fun onPermissionResult(permission: String, granted: Boolean) {

    }
}
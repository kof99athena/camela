package com.anehta.camela.feature.preview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class PreviewViewModel : ViewModel() {
    private val _permissionGranted = MutableLiveData<Boolean>()
    //custom getter : read only _permissionGranted
    val permissionGranted: LiveData<Boolean> get() = _permissionGranted

    fun setPermissionGranted(isGranted: Boolean){
        _permissionGranted.value = isGranted
    }
}
package com.anehta.camela.feature.preview.repositories.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anehta.camela.feature.preview.repositories.PreviewRepository

class PreviewRepositoryImpl : PreviewRepository {
    private val _permissionGranted = MutableLiveData<Boolean>()
    //custom getter : read only _permissionGranted
    val permissionGranted: LiveData<Boolean> get() = _permissionGranted

    override fun setPermissionGranted(isGranted: Boolean) {
        _permissionGranted.value = isGranted
    }
}
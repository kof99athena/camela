package com.anehta.camela.feature.preview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anehta.camela.feature.preview.interactors.PreviewInteractor
import com.anehta.camela.models.requests.RequestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(private val interactor: PreviewInteractor) :
    ViewModel() {

    private val _requestModel = MutableLiveData<RequestModel>()
    val requestModel: LiveData<RequestModel>
        get() = _requestModel

    init {
        _requestModel.value = RequestModel(false)
    }

    fun getPermissionStatus() {
        val permissionStatus = interactor.getPermissionStatus()
        _requestModel.value = permissionStatus
    }

    fun setPermissionStatus(isGranted: Boolean) {
        _requestModel.value = RequestModel(isGranted)
    }
}
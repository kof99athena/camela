package com.anehta.camela.feature.preview.interactors.impl

import com.anehta.camela.feature.preview.interactors.PreviewInteractor
import com.anehta.camela.feature.preview.repositories.PreviewRepository
import com.anehta.camela.models.requests.RequestModel
import javax.inject.Inject

class PreviewInteractorImpl @Inject constructor(private val repository: PreviewRepository) :
    PreviewInteractor {
    override fun getPermissionStatus(): RequestModel {
        return repository.getPermissionStatus()
    }

    override fun setPermissionStatus(requestModel: RequestModel) {
        repository.setPermissionStatus(requestModel)
    }
}
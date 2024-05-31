package com.anehta.camela.feature.preview.interactors.impl

import com.anehta.camela.feature.preview.interactors.PreviewInteractor
import com.anehta.camela.feature.preview.repositories.PreviewRepository
import com.anehta.camela.models.requests.PermissionRequest
import javax.inject.Inject

class PreviewInteractorImpl @Inject constructor(private val repository: PreviewRepository) :
    PreviewInteractor {
    override fun getPermissionStatus(): PermissionRequest {
        return repository.getPermissionStatus()
    }

    override fun setPermissionStatus(permissionRequest: PermissionRequest) {
        repository.setPermissionStatus(permissionRequest)
    }
}
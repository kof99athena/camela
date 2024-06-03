package com.anehta.camela.feature.preview.repositories.impl

import com.anehta.camela.feature.preview.repositories.PreviewRepository
import com.anehta.camela.models.requests.PermissionRequest
import javax.inject.Inject

class PreviewRepositoryImpl @Inject constructor() : PreviewRepository {
    private var permissionGranted: Boolean = false

    override fun getPermissionStatus(): PermissionRequest {
        return PermissionRequest(permissionGranted)
    }

    override fun setPermissionStatus(permissionRequest: PermissionRequest) {
        permissionGranted = permissionRequest.isGranted
    }
}
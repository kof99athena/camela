package com.anehta.camela.feature.preview.repositories.impl

import com.anehta.camela.feature.preview.repositories.PreviewRepository
import com.anehta.camela.models.requests.PermissionRequest
import com.anehta.camela.utils.ScreenUtil
import javax.inject.Inject

class PreviewRepositoryImpl @Inject constructor() : PreviewRepository {
    private var permissionGranted: Boolean = false
    private var previewRatio: String = "Full"

    override fun getPermissionStatus(): PermissionRequest {
        return PermissionRequest(permissionGranted)
    }

    override fun setPermissionStatus(permissionRequest: PermissionRequest) {
        permissionGranted = permissionRequest.isGranted
    }
}
package com.anehta.camela.feature.preview.interactors

import com.anehta.camela.models.requests.PermissionRequest

interface PreviewInteractor {
    fun getPermissionStatus(): PermissionRequest
    fun setPermissionStatus(permissionRequest: PermissionRequest)
}
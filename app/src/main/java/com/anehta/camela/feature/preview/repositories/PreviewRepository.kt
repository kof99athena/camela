package com.anehta.camela.feature.preview.repositories

import com.anehta.camela.models.requests.PermissionRequest

interface PreviewRepository {
    fun getPermissionStatus(): PermissionRequest
    fun setPermissionStatus(permissionRequest: PermissionRequest)
}
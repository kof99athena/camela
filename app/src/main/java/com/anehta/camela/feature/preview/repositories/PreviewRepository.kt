package com.anehta.camela.feature.preview.repositories

import com.anehta.camela.models.requests.PermissionRequest
import com.anehta.camela.utils.ScreenUtil

interface PreviewRepository {
    fun getPermissionStatus(): PermissionRequest
    fun setPermissionStatus(permissionRequest: PermissionRequest)
}
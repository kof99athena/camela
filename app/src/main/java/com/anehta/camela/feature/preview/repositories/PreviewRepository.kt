package com.anehta.camela.feature.preview.repositories

import com.anehta.camela.models.requests.RequestModel

interface PreviewRepository {
    fun getPermissionStatus(): RequestModel
    fun setPermissionStatus(requestModel: RequestModel)
}
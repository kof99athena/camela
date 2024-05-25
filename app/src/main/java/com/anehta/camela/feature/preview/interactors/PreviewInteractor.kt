package com.anehta.camela.feature.preview.interactors

import com.anehta.camela.models.requests.RequestModel

interface PreviewInteractor {
    fun getPermissionStatus(): RequestModel
    fun setPermissionStatus(requestModel: RequestModel)
}
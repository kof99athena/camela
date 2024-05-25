package com.anehta.camela.feature.preview.repositories.impl

import com.anehta.camela.feature.preview.repositories.PreviewRepository
import com.anehta.camela.models.requests.RequestModel
import javax.inject.Inject

class PreviewRepositoryImpl @Inject constructor() : PreviewRepository {
    private var permissionGranted: Boolean = false

    override fun getPermissionStatus(): RequestModel {
        return RequestModel(permissionGranted)
    }

    override fun setPermissionStatus(requestModel: RequestModel) {
        permissionGranted = requestModel.isGranted
    }
}
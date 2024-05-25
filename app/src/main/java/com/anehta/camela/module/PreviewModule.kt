package com.anehta.camela.module

import com.anehta.camela.feature.preview.interactors.PreviewInteractor
import com.anehta.camela.feature.preview.interactors.impl.PreviewInteractorImpl
import com.anehta.camela.feature.preview.repositories.PreviewRepository
import com.anehta.camela.feature.preview.repositories.impl.PreviewRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreviewModule {

    @Binds
    abstract fun bindPreviewRepository(
        previewRepositoryImpl: PreviewRepositoryImpl
    ): PreviewRepository

    @Binds
    abstract fun bindPreviewInteractor(
        previewInteractorImpl: PreviewInteractorImpl
    ): PreviewInteractor
}
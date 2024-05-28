package com.anehta.camela

import android.database.Observable
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.camera.core.impl.Observable.Observer
import com.anehta.camela.feature.preview.interactors.PreviewInteractor
import com.anehta.camela.feature.preview.repositories.PreviewRepository
import com.anehta.camela.feature.preview.viewmodel.PreviewViewModel
import com.anehta.camela.models.requests.RequestModel
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PreviewViewModel
    private val interactor: PreviewInteractor = mock()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = PreviewViewModel(interactor)
    }

    @Test
    fun `test live data updates correctly`() = testScope.runBlockingTest {
        // given
        val expectedData = RequestModel(true)
        whenever(interactor.getPermissionStatus()).thenReturn(expectedData)

        // LiveData 관찰자 설정
        val observer: androidx.lifecycle.Observer<in RequestModel> = mock()
        viewModel.requestModel.observeForever(observer)

        // when
        viewModel.getPermissionStatus()

        // then
        verify(observer).onChanged(expectedData)
        assert(viewModel.requestModel.value == expectedData)
    }
}
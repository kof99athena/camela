package com.anehta.camela

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anehta.camela.feature.preview.repositories.PreviewRepository
import com.anehta.camela.feature.preview.viewmodel.PreviewViewModel
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.kotlin.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PreviewViewModel
    private val repository: PreviewRepository = mock()
//    private val testDispatcher = TestCoroutineDispatcher()
//    private val testScope = TestCoroutineScope(testDispatcher)

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
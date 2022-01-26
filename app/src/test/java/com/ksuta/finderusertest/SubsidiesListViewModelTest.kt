package com.ksuta.finderusertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ksuta.finderusertest.screens.details.DetailUserViewModel
import com.ksuta.finderusertest.screens.details.IDetailRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import androidx.lifecycle.Observer
import com.ksuta.finderusertest.screens.details.DetailModel
import com.ksuta.finderusertest.utils.LifeCycleTestOwner
import junit.framework.Assert.assertTrue
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runBlockingTest
import org.mockito.Mockito

@RunWith(RobolectricTestRunner::class)
class DetailUserViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repo: IDetailRepository = mock()
    private lateinit var viewModel: DetailUserViewModel

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner

    @Before
    fun setUp() {
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()
        viewModel = DetailUserViewModel(1, repo)
    }

    @Test
    fun `onGetSubsidies successful`() {
        val observer: Observer<DetailModel> = mock()
        viewModel.setModel.observeForever(observer)
        coroutineTestRule.testDispatcher.runBlockingTest {

            lifeCycleTestOwner.onResume()
            Mockito.`when`(repo.getUser(1)).thenReturn(null)
        }

        assertNotNull(viewModel.setModel)
        assertTrue(viewModel.setModel.hasObservers())
    }
}

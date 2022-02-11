package com.ksuta.finderusertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ksuta.finderusertest.screens.details.DetailUserViewModel
import com.ksuta.finderusertest.screens.details.IDetailRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.lifecycle.Observer
import com.ksuta.finderusertest.screens.details.DetailModel
import com.ksuta.finderusertest.screens.search.UserModel
import com.ksuta.finderusertest.utils.LifeCycleTestOwner
import com.ksuta.finderusertest.utils.getOrAwaitValue
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.mockito.Mockito
import org.mockito.kotlin.*

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

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get Details successful`() {
        val observer: Observer<DetailModel> = mock()
        viewModel.setModel.observeForever(observer)

        coroutineTestRule.testDispatcher.runBlockingTest {

            lifeCycleTestOwner.onResume()
            Mockito.`when`(repo.getUser(1)).thenReturn(null)
        }

        assertNotNull(viewModel.setModel)
        assertTrue(viewModel.setModel.hasObservers())

        viewModel.setModel.removeObserver(observer)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `whether the values of the parts are the same DetailsModel`() {
        val observer: Observer<DetailModel> = mock()
        val modelMock = UserModel(1, "100", "lol", "https://webref.ru/assets/images/recipe/image-link.png", null, null, null)
        viewModel.setModel.observeForever(observer)

        coroutineTestRule.testDispatcher.runBlockingTest {
            lifeCycleTestOwner.onResume()
            Mockito.`when`(repo.getUser(1)).thenReturn(modelMock)
        }
        val expected= DetailModel("lol","https://webref.ru/assets/images/recipe/image-link.png",null,null,null,null)
        assertNotNull(viewModel.setModel)

        viewModel.setModel.value= expected
        assertEquals( viewModel.setModel.getOrAwaitValue(), expected)

        viewModel.setModel.removeObserver(observer)
    }
}

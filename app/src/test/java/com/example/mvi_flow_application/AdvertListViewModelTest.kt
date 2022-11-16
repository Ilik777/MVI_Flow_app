package com.example.mvi_flow_application

import com.example.mvi_flow_application.advertlist.domain.AdvertsRepository
import com.example.mvi_flow_application.advertlist.domain.DeleteAllAdvertsUseCase
import com.example.mvi_flow_application.advertlist.domain.LoadAdvertUseCase
import com.example.mvi_flow_application.advertlist.domain.model.AdvertData
import com.example.mvi_flow_application.advertlist.domain.model.AdvertType
import com.example.mvi_flow_application.advertlist.presentation.AdvertListReducer
import com.example.mvi_flow_application.advertlist.presentation.AdvertListViewModel
import com.example.mvi_flow_application.advertlist.presentation.mapper.AdvertDataMapper
import com.example.mvi_flow_application.advertlist.presentation.mapper.AdvertViewDataMapper
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertListIntent
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertListViewState
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertViewData
import com.example.mvi_flow_application.util.Response
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class AdvertListViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val advertsRepositoryMock: AdvertsRepository = mock(AdvertsRepository::class.java)
    private lateinit var viewModel: AdvertListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = AdvertListViewModel(
            loadAdvertUseCase = LoadAdvertUseCase(
                advertsRepository = advertsRepositoryMock
            ),
            deleteAdvertUseCase = DeleteAllAdvertsUseCase(
                advertsRepository = advertsRepositoryMock
            ),
            advertDataMapper = AdvertDataMapper(),
            reducer = AdvertListReducer(
                advertViewDataMapper = AdvertViewDataMapper()
            )
        )
    }

    @Test
    fun onLoadAdvertsTest() = runTest {
        val viewState = AdvertListViewState(
            isLoading = false,
            advertList = listOf(
                AdvertViewData(
                    id = 0,
                    title = "Vova",
                    subtitle = "iOS developer",
                    imageRes = R.drawable.ic_ios
                ),
                AdvertViewData(
                    id = 1,
                    title = "Temirlan",
                    subtitle = "Android developer",
                    imageRes = R.drawable.ic_android
                )
            )
        )
        `when`(
            advertsRepositoryMock.loadAdverts()
        ).thenReturn(
            flowOf(
                Response.Success(
                    listOf(
                        AdvertData(
                            id = 0,
                            title = "Vova",
                            subtitle = "iOS developer",
                            type = AdvertType.IOS
                        ),
                        AdvertData(
                            id = 1,
                            title = "Temirlan",
                            subtitle = "Android developer",
                            type = AdvertType.ANDROID
                        )
                    )
                )
            )
        )
        viewModel.onIntentCalled(AdvertListIntent.LoadAdvertList)


        assertEquals(
            viewState,
            viewModel.stateFlow.value
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
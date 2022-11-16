package com.example.mvi_flow_application.advertlist.presentation.reducer

import com.example.mvi_flow_application.advertlist.domain.model.AdvertListResult
import com.example.mvi_flow_application.advertlist.presentation.mapper.AdvertViewDataMapper
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertListViewState

class LoadAllAdvertReducer(
        private val advertViewDataMapper: AdvertViewDataMapper
) : AdvertListReducerStrategy<AdvertListResult.LoadAllAdvertsResult> {

    override fun reduce(
            previousState: AdvertListViewState,
            result: AdvertListResult.LoadAllAdvertsResult
    ): AdvertListViewState = when (result) {
        is AdvertListResult.LoadAllAdvertsResult.Success -> {
            previousState.copy(
                    isLoading = false,
                    advertList = advertViewDataMapper.map(result.advertList),
            )
        }
        is AdvertListResult.LoadAllAdvertsResult.Failure -> previousState.copy(
                isLoading = false,
        )
        is AdvertListResult.LoadAllAdvertsResult.Loading -> previousState.copy(
                isLoading = true,
        )
    }
}
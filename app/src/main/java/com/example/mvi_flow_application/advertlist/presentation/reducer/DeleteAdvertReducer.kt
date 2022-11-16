package com.example.mvi_flow_application.advertlist.presentation.reducer

import com.example.mvi_flow_application.advertlist.domain.model.AdvertListResult
import com.example.mvi_flow_application.advertlist.presentation.mapper.AdvertViewDataMapper
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertListViewState

class DeleteAdvertReducer(
        private val advertViewDataMapper: AdvertViewDataMapper
) : AdvertListReducerStrategy<AdvertListResult.DeleteAdvertResult> {

    override fun reduce(
            previousState: AdvertListViewState,
            result: AdvertListResult.DeleteAdvertResult
    ): AdvertListViewState = when (result) {
        is AdvertListResult.DeleteAdvertResult.Success -> {
            previousState.copy(
                    isLoading = false,
                    advertList = advertViewDataMapper.map(result.advertList),
            )
        }
        is AdvertListResult.DeleteAdvertResult.Failure -> previousState.copy(
                isLoading = false,
        )
        is AdvertListResult.DeleteAdvertResult.Loading -> previousState.copy(
                isLoading = true,
        )
    }
}
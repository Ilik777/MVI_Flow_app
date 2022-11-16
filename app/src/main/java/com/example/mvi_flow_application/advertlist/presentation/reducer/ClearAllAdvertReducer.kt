package com.example.mvi_flow_application.advertlist.presentation.reducer

import com.example.mvi_flow_application.advertlist.domain.model.AdvertListResult
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertListViewState

class ClearAllAdvertReducer : AdvertListReducerStrategy<AdvertListResult.ClearAdvertListResult> {

    override fun reduce(
            previousState: AdvertListViewState,
            result: AdvertListResult.ClearAdvertListResult
    ): AdvertListViewState = when (result) {
        is AdvertListResult.ClearAdvertListResult.Success -> {
            previousState.copy(
                    isLoading = false,
                    advertList = emptyList(),
            ) // NOTION
        }
        is AdvertListResult.ClearAdvertListResult.Failure -> previousState.copy(
                isLoading = false,
        )
        is AdvertListResult.ClearAdvertListResult.Clearing -> previousState.copy(
                isLoading = true,
        )
    }
}
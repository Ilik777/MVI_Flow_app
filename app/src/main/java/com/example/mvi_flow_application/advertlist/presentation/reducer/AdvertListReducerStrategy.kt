package com.example.mvi_flow_application.advertlist.presentation.reducer

import com.example.mvi_flow_application.advertlist.domain.model.AdvertListResult
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertListViewState

interface AdvertListReducerStrategy<in E : AdvertListResult> {

    fun reduce(
            previousState: AdvertListViewState,
            result: E
    ): AdvertListViewState
}
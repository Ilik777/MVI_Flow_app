package com.example.mvi_flow_application.advertlist

import com.example.mvi_flow_application.mvibase.MviViewState

data class AdvertListViewState(
    val isLoading: Boolean,
    val advertList: List<AdvertViewData>,
    val error: Throwable?
) : MviViewState {

    companion object {
        fun idle(): AdvertListViewState = AdvertListViewState(
            isLoading = false,
            advertList = emptyList(),
            error = null
        )
    }
}

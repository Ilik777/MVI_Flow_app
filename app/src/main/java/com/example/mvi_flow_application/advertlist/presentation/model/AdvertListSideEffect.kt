package com.example.mvi_flow_application.advertlist.presentation.model

import com.example.mvi_flow_application.mvibase.MviSideEffect

sealed class AdvertListSideEffect : MviSideEffect {

    data class OpenAdvertDetails(
            val advertViewData: AdvertViewData
    ) : AdvertListSideEffect()

    data class ShowToast(
            val text: String
    ) : AdvertListSideEffect()

    data class ShowError(
            val errorText: String
    ) : AdvertListSideEffect()
}
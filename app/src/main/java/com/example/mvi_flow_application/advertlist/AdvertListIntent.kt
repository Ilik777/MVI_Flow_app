package com.example.mvi_flow_application.advertlist

import com.example.mvi_flow_application.mvibase.MviIntent

sealed class AdvertListIntent : MviIntent {
    object LoadAdvertList : AdvertListIntent()
    object ClearAdvertList : AdvertListIntent()

    data class OpenAdvertDetails(
        val advertViewData: AdvertViewData
    ) : AdvertListIntent()
}

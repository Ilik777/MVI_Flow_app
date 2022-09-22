package com.example.mvi_flow_application.advertlist

import com.example.mvi_flow_application.mvibase.MviAction

sealed class AdvertListAction : MviAction {
    object LoadAdvertListAction : AdvertListAction()
    object ClearAdvertListAction : AdvertListAction()
    data class OpenAdvertDetailsAction(
        val advertViewData: AdvertViewData
    ) : AdvertListAction()
}

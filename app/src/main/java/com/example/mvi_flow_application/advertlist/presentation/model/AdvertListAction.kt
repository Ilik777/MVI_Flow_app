package com.example.mvi_flow_application.advertlist.presentation.model

import com.example.mvi_flow_application.mvibase.MviAction

sealed class AdvertListAction : MviAction {

    object LoadAdvertListAction : AdvertListAction()
    object ClearAdvertListAction : AdvertListAction()

    data class OpenAdvertDetailsAction(
        val advertViewData: AdvertViewData
    ) : AdvertListAction()

    data class DeleteAdvertDetailsAction(
        val advertViewData: AdvertViewData,
        val advertViewDataList: List<AdvertViewData>
    ) : AdvertListAction()
}

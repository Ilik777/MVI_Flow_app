package com.example.mvi_flow_application.advertlist.presentation

import com.example.mvi_flow_application.advertlist.presentation.model.AdvertViewData

interface AdvertClickedCallBack {

    fun onAdvertItemClicked(advertViewData: AdvertViewData)

    fun onFireButtonClicked(advertViewData: AdvertViewData)
}
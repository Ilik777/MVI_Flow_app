package com.example.mvi_flow_application.advertlist.presentation.mapper

import com.example.mvi_flow_application.R
import com.example.mvi_flow_application.advertlist.domain.model.AdvertData
import com.example.mvi_flow_application.advertlist.domain.model.AdvertType
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertViewData

class AdvertDataMapper {

    fun map(
            advertViewDataList: List<AdvertViewData>
    ): List<AdvertData> = advertViewDataList.map {
        AdvertData(
                id = it.id,
                title = it.title,
                subtitle = it.subtitle,
                type = getAdvertType(it.imageRes)
        )
    }

    private fun getAdvertType(
            imageRes: Int
    ): AdvertType = when (imageRes) {
        R.drawable.ic_ios -> AdvertType.IOS
        R.drawable.ic_android -> AdvertType.ANDROID
        else -> AdvertType.UNKNOWN
    }
}
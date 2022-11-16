package com.example.mvi_flow_application.advertlist.presentation.mapper

import com.example.mvi_flow_application.R
import com.example.mvi_flow_application.advertlist.domain.model.AdvertData
import com.example.mvi_flow_application.advertlist.domain.model.AdvertType
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertViewData

class AdvertViewDataMapper {

    fun map(
            advertDataList: List<AdvertData>
    ): List<AdvertViewData> = advertDataList.map {
        AdvertViewData(
                id = it.id,
                title = it.title,
                subtitle = it.subtitle,
                imageRes = getImageRes(it.type)
        )
    }

    private fun getImageRes(
            type: AdvertType
    ): Int = when (type) {
        AdvertType.IOS -> R.drawable.ic_ios
        AdvertType.ANDROID -> R.drawable.ic_android
        AdvertType.UNKNOWN -> R.drawable.ic_unknown
    }
}
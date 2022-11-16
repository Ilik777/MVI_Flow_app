package com.example.mvi_flow_application.advertlist.data

import com.example.mvi_flow_application.advertlist.domain.model.AdvertData
import com.example.mvi_flow_application.advertlist.domain.model.AdvertType

class ApiAdvertDataMapper {

    fun map(
            it: ApiAdvertData
    ): AdvertData = AdvertData(
            id = it.id ?: 0,
            type = getType(it.type),
            title = it.title.orEmpty(),
            subtitle = it.subTitle.orEmpty()
    )

    private fun getType(
            type: String?
    ): AdvertType = when (type) {
        "ios" -> AdvertType.IOS
        "android" -> AdvertType.ANDROID
        else -> AdvertType.UNKNOWN
    }
}
package com.example.mvi_flow_application.advertlist.domain.model

data class AdvertData(
        val id: Int,
        val type: AdvertType,
        val title: String,
        val subtitle: String
)

enum class AdvertType {
    IOS,
    ANDROID,
    UNKNOWN
}
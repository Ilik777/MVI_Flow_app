package com.example.mvi_flow_application.advertlist.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiAdvertData(
        @JsonProperty("versionCode")
        val versionCode: Int?,
        @JsonProperty("id")
        val id: Int?,
        @JsonProperty("title")
        val title: String?,
        @JsonProperty("subtitle")
        val subTitle: String?,
        @JsonProperty("type")
        val type: String?,
)
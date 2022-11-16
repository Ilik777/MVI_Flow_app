package com.example.mvi_flow_application.advertlist.presentation.model

import android.os.Parcelable
import com.example.mvi_flow_application.mvibase.MviViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdvertListViewState(
    val isLoading: Boolean,
    val advertList: List<AdvertViewData>
) : MviViewState, Parcelable {

    companion object {
        fun idle(): AdvertListViewState = AdvertListViewState(
            isLoading = false,
            advertList = emptyList()
        )
    }
}
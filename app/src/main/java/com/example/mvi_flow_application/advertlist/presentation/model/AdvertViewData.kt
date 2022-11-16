package com.example.mvi_flow_application.advertlist.presentation.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdvertViewData(
        val id: Int,
        @DrawableRes
        val imageRes: Int,
        val title: String,
        val subtitle: String
) : Parcelable
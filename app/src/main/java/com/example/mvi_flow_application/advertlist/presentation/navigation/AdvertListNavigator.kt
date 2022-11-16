package com.example.mvi_flow_application.advertlist.presentation.navigation

import android.app.Activity
import android.content.Intent
import com.example.mvi_flow_application.advertdetails.AdvertDetailsActivity
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertViewData

class AdvertListNavigator {

    var activity: Activity? = null

    fun openAdvertDetails(
            advertData: AdvertViewData
    ) {
        activity?.startActivity(
                Intent(activity, AdvertDetailsActivity::class.java).apply {
                    putExtra("advertData" , advertData)
                }
        )
    }
}
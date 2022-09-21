package com.example.mvi_flow_application.advertlist

import androidx.lifecycle.ViewModel
import com.example.mvi_flow_application.mvibase.MviViewModel
import kotlinx.coroutines.flow.Flow

class AdvertListViewModel(

) : ViewModel(), MviViewModel<AdvertListIntent, AdvertListViewState> {

    override fun processIntents(intents: Flow<AdvertListIntent>) {
        TODO("Not yet implemented")
    }

    override fun states(): Flow<AdvertListViewState> {
        TODO("Not yet implemented")
    }
}
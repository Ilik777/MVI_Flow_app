package com.example.mvi_flow_application.advertlist

import androidx.lifecycle.ViewModel
import com.example.mvi_flow_application.mvibase.MviViewModel
import kotlinx.coroutines.flow.*

class AdvertListViewModel(

) : ViewModel(), MviViewModel<AdvertListIntent, AdvertListViewState> {

    private val intentsSharedFlow: SharedFlow<AdvertListIntent> = MutableSharedFlow()
    private val statesFlow: Flow<AdvertListViewState> = compose()

    override fun processIntents(intents: Flow<AdvertListIntent>) {
        TODO("Not yet implemented")
    }

    override fun states(): Flow<AdvertListViewState> = statesFlow

    private fun compose(): Flow<AdvertListViewState> = intentsSharedFlow
        .map {
            actionFromIntent(it)
        }
        .scan(AdvertListViewState.idle())
        .distinctUntilChanged()

    private fun actionFromIntent(
        intent: AdvertListIntent
    ): AdvertListAction = when (intent) {
        is AdvertListIntent.LoadAdvertList -> AdvertListAction.LoadAdvertListAction
        is AdvertListIntent.ClearAdvertList -> AdvertListAction.ClearAdvertListAction
        is AdvertListIntent.OpenAdvertDetails -> AdvertListAction.OpenAdvertDetailsAction(
            advertViewData = intent.advertViewData
        )
    }
}
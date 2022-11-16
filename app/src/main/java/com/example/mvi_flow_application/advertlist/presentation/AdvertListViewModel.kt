package com.example.mvi_flow_application.advertlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_flow_application.advertlist.domain.DeleteAllAdvertsUseCase
import com.example.mvi_flow_application.advertlist.domain.LoadAdvertUseCase
import com.example.mvi_flow_application.advertlist.domain.model.AdvertListResult
import com.example.mvi_flow_application.advertlist.presentation.mapper.AdvertDataMapper
import com.example.mvi_flow_application.advertlist.presentation.model.*
import com.example.mvi_flow_application.mvibase.MviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
//Store 
class AdvertListViewModel(
    private val loadAdvertUseCase: LoadAdvertUseCase, //middleware
    private val deleteAdvertUseCase: DeleteAllAdvertsUseCase,
    private val advertDataMapper: AdvertDataMapper,
    private val reducer: AdvertListReducer
) : ViewModel(), MviViewModel<AdvertListIntent, AdvertListViewState, AdvertListSideEffect> {

    private val intents: Channel<AdvertListIntent> = Channel(Channel.UNLIMITED)
    override val states: LiveData<AdvertListViewState>
        get() = _states
    private val _states: MutableLiveData<AdvertListViewState> = MutableLiveData(createIdleState())

    override val stateFlow: StateFlow<AdvertListViewState>
        get() = _stateFlow.asStateFlow()
    private val _stateFlow: MutableStateFlow<AdvertListViewState> =
        MutableStateFlow(createIdleState())

    override val sideEffect: SharedFlow<AdvertListSideEffect>
        get() = _sideEffect.asSharedFlow()
    private val _sideEffect: MutableSharedFlow<AdvertListSideEffect> = MutableSharedFlow()

    private var navigationCallBack: ((advertData: AdvertViewData) -> Unit)? = null

    init {
        handlerIntent()
    }

    fun setNavigationCallBack(
        callback: ((advertData: AdvertViewData) -> Unit)?
    ) {
        navigationCallBack = callback
    }

    override fun onIntentCalled(intent: AdvertListIntent) {
        viewModelScope.launch {
            intents.send(intent)
        }
    }

    fun createIdleState(
        restoredState: AdvertListViewState? = null
    ): AdvertListViewState = restoredState ?: AdvertListViewState.idle()

    private fun handlerIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect {
                launch(Dispatchers.IO) {
                    handleAction(getActionFromIntent(it))
                }
            }
        }
    }

    private fun getActionFromIntent(
        intent: AdvertListIntent
    ): AdvertListAction = when (intent) {
        is AdvertListIntent.LoadAdvertList -> AdvertListAction.LoadAdvertListAction
        is AdvertListIntent.ClearAdvertList -> AdvertListAction.ClearAdvertListAction
        is AdvertListIntent.OpenAdvertDetails -> AdvertListAction.OpenAdvertDetailsAction(
            advertViewData = intent.advertViewData
        )
        is AdvertListIntent.DeleteAdvertDetails -> AdvertListAction.DeleteAdvertDetailsAction(
            advertViewData = intent.advertViewData,
            advertViewDataList = stateFlow.value.advertList
        )
    }

    private suspend fun handleAction(actionFromIntent: AdvertListAction) {
        when (actionFromIntent) {
            is AdvertListAction.LoadAdvertListAction -> {
                loadAdvertUseCase.execute().collect {
                    //extension?
                    if (it is AdvertListResult.LoadAllAdvertsResult.Failure) {
                        updateSideEffect(AdvertListSideEffect.ShowError(it.error.message ?: ""))
                    }
                    updateState { previousState ->
                        reducer.reduce(
                            previousState,
                            it
                        )
                    }
                }
            }
            is AdvertListAction.ClearAdvertListAction -> {
                updateState { previousState ->
                    reducer.reduce(
                        previousState,
                        AdvertListResult.ClearAdvertListResult.Success
                    )
                }
            }
            is AdvertListAction.OpenAdvertDetailsAction -> {
                updateSideEffect(
                    AdvertListSideEffect.ShowToast("Open user id= " + actionFromIntent.advertViewData.id)
                )
                updateSideEffect(
                    AdvertListSideEffect.OpenAdvertDetails(
                        actionFromIntent.advertViewData
                    )
                )

//                //navigator
//                navigationCallBack?.invoke(actionFromIntent.advertViewData)
            }
            is AdvertListAction.DeleteAdvertDetailsAction -> {
                deleteAdvertUseCase.execute(
                    advertId = actionFromIntent.advertViewData.id,
                    advertList = advertDataMapper.map(actionFromIntent.advertViewDataList)
                ).collect {
                    if (it is AdvertListResult.DeleteAdvertResult.Failure) {
                        updateSideEffect(AdvertListSideEffect.ShowError(it.error.message ?: ""))
                    }
                    updateState { previousState ->
                        reducer.reduce(
                            previousState,
                            it
                        )
                    }
                }
            }
        }
    }

    private suspend fun updateState(
        handler: suspend (intent: AdvertListViewState) -> AdvertListViewState
    ) {
//        _state.postValue(handler(states.value!!))
        _stateFlow.emit(handler(stateFlow.value))
    }

    private suspend fun updateSideEffect(
        sideEffect: AdvertListSideEffect
    ) {
        _sideEffect.emit(sideEffect)
    }
}
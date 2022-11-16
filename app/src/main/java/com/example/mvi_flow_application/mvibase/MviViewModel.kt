package com.example.mvi_flow_application.mvibase

import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MviViewModel<I : MviIntent, S : MviViewState, E : MviSideEffect> {
    val states: LiveData<S>
    val stateFlow: StateFlow<S>
    val sideEffect: SharedFlow<E>

    fun onIntentCalled(intent: I)
}
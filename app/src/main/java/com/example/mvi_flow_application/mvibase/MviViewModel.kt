package com.example.mvi_flow_application.mvibase

import kotlinx.coroutines.flow.Flow

interface MviViewModel<I : MviIntent, S : MviViewState> {

	fun processIntents(intents: Flow<I>)
	fun states(): Flow<S>
}
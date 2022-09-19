package com.example.mvi_flow_application.mvibase

import kotlinx.coroutines.flow.Flow

interface MviView<I : MviIntent, in S : MviViewState> {

	fun intents(): Flow<I>
	fun render(state: S)
}
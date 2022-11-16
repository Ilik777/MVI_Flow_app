package com.example.mvi_flow_application.mvibase

interface MviView<S : MviViewState, E: MviSideEffect> {

    fun render(state: S)

    fun handleSideEffect(sideEffect: E)
}
package com.example.mvi_flow_application.advertlist.presentation

import com.example.mvi_flow_application.advertlist.domain.model.AdvertListResult
import com.example.mvi_flow_application.advertlist.presentation.mapper.AdvertViewDataMapper
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertListViewState
import com.example.mvi_flow_application.advertlist.presentation.reducer.AdvertListReducerStrategy
import com.example.mvi_flow_application.advertlist.presentation.reducer.ClearAllAdvertReducer
import com.example.mvi_flow_application.advertlist.presentation.reducer.DeleteAdvertReducer
import com.example.mvi_flow_application.advertlist.presentation.reducer.LoadAllAdvertReducer

class AdvertListReducer(
        private val advertViewDataMapper: AdvertViewDataMapper
) {

    fun reduce(
            previousState: AdvertListViewState,
            result: AdvertListResult
    ): AdvertListViewState = when (result) {
        is AdvertListResult.LoadAllAdvertsResult -> {
            LoadAllAdvertReducer(advertViewDataMapper).reduce(previousState, result)
        }
        is AdvertListResult.ClearAdvertListResult -> {
            ClearAllAdvertReducer().reduce(previousState, result)
        }
        is AdvertListResult.DeleteAdvertResult -> {
            DeleteAdvertReducer(advertViewDataMapper).reduce(previousState, result)
        }
    }
}
// It's called a reducer because it's the type of function you would pass to Array.reduce(operation: (acc: S, T) -> S).
// It's very important that the reducer stays pure. Things you should never do inside a reducer:
//
//Mutate its arguments;
//Perform side effects like API calls and routing transitions;
//Call non-pure functions, e.g. Date.now() or Math.random().
//We'll explore how to perform side effects in the advanced walkthrough.
//For now, just remember that the reducer must be pure.
//Given the same arguments, it should calculate the next state and return it.
//No surprises. No side effects. No API calls. No mutations. Just a calculation.
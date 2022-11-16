package com.example.mvi_flow_application.advertlist.domain.model

import com.example.mvi_flow_application.mvibase.MviResult

sealed class AdvertListResult : MviResult {

    sealed class LoadAllAdvertsResult : AdvertListResult() {
        object Loading : LoadAllAdvertsResult()
        data class Success(val advertList: List<AdvertData>) : LoadAllAdvertsResult()
        data class Failure(val error: Throwable) : LoadAllAdvertsResult()
    }

    sealed class ClearAdvertListResult : AdvertListResult() {
        object Clearing : ClearAdvertListResult()
        object Success : ClearAdvertListResult()
        data class Failure(val error: Throwable) : ClearAdvertListResult()
    }

    sealed class DeleteAdvertResult : AdvertListResult() {
        data class Success(
                val advertList: List<AdvertData>
        ) : DeleteAdvertResult()

        object Loading : DeleteAdvertResult()
        data class Failure(val error: Throwable) : DeleteAdvertResult()
    }
}
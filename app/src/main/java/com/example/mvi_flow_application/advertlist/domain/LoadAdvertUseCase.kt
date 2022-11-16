package com.example.mvi_flow_application.advertlist.domain

import com.example.mvi_flow_application.advertlist.domain.model.AdvertListResult
import com.example.mvi_flow_application.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class LoadAdvertUseCase( // Processor
        private val advertsRepository: AdvertsRepository
) {

    fun execute(): Flow<AdvertListResult> = advertsRepository.loadAdverts()
            .map { response ->
                when (response) {
                    is Response.Success -> AdvertListResult.LoadAllAdvertsResult.Success(
                            response.result
                    )
                    is Response.Error -> AdvertListResult.LoadAllAdvertsResult.Failure(
                            response.error
                    )
                }
            }
            .onStart {
                emit(AdvertListResult.LoadAllAdvertsResult.Loading)
//                emit(throw Error("FLOW ERROR"))
                delay(1000)
            }
            .catch { error ->
                emit(AdvertListResult.LoadAllAdvertsResult.Failure(error = error))
            }
}
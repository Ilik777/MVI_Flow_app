package com.example.mvi_flow_application.advertlist.domain

import com.example.mvi_flow_application.advertlist.domain.model.AdvertData
import com.example.mvi_flow_application.advertlist.domain.model.AdvertListResult
import com.example.mvi_flow_application.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class DeleteAllAdvertsUseCase(
        private val advertsRepository: AdvertsRepository
) {

    fun execute(
            advertId: Int,
            advertList: List<AdvertData>
    ): Flow<AdvertListResult> = advertsRepository.deleteUser(advertId)
            .map { response ->
                when (response) {
                    is Response.Success -> AdvertListResult.DeleteAdvertResult.Success(
                            advertList.filter {
                                it.id != advertId
                            }
                    )
                    is Response.Error -> AdvertListResult.DeleteAdvertResult.Failure(
                            response.error
                    )
                }
            }
            .onStart {
                emit(AdvertListResult.DeleteAdvertResult.Loading)
//                emit(throw Error("FLOW ERROR"))
                delay(1000)
            }
            .catch { error ->
                emit(AdvertListResult.DeleteAdvertResult.Failure(error = error))
            }
}
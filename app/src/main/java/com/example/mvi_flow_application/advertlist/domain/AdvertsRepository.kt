package com.example.mvi_flow_application.advertlist.domain

import com.example.mvi_flow_application.advertlist.domain.model.AdvertData
import com.example.mvi_flow_application.util.Response
import kotlinx.coroutines.flow.Flow

interface AdvertsRepository {

    fun loadAdverts(): Flow<Response<List<AdvertData>, Exception>>

    fun deleteUser(userId: Int): Flow<Response<Unit, Exception>>
}
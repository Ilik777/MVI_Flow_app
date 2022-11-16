package com.example.mvi_flow_application.advertlist.data

import com.example.mvi_flow_application.advertlist.domain.AdvertsRepository
import com.example.mvi_flow_application.advertlist.domain.model.AdvertData
import com.example.mvi_flow_application.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.IOException
import kotlin.random.Random

class DefaultAdvertsRepository : AdvertsRepository {

    private val apiMapper: ApiAdvertDataMapper = ApiAdvertDataMapper()

    override fun loadAdverts(): Flow<Response<List<AdvertData>, Exception>> = try {
//            throw java.lang.Exception("SERVER RESPONSE ERROR 503")

        flowOf(
                Response.Success(
                        listOf(
                                ApiAdvertData(
                                        type = "android",
                                        id = 1,
                                        title = "Oleg",
                                        subTitle = "Android TechLead",
                                        versionCode = 1
                                ),
                                ApiAdvertData(
                                        type = "ios",
                                        id = 2,
                                        title = "Gaini",
                                        subTitle = "iOS TechLead",
                                        versionCode = 1
                                ),
                                ApiAdvertData(
                                        type = "ios",
                                        id = 3,
                                        title = "Vova",
                                        subTitle = "iOS developer",
                                        versionCode = 1
                                ),
                                ApiAdvertData(
                                        type = "android",
                                        id = 4,
                                        title = "Temirlan",
                                        subTitle = "Android developer",
                                        versionCode = 1
                                ),
                                ApiAdvertData(
                                        type = "ios",
                                        id = 5,
                                        title = "Nikolai",
                                        subTitle = "iOS developer",
                                        versionCode = 1
                                ),
                                ApiAdvertData(
                                        type = "android",
                                        id = 6,
                                        title = "Amanzhol",
                                        subTitle = "Android Developer",
                                        versionCode = 1
                                ),
                                ApiAdvertData(
                                        type = "QA",
                                        id = 7,
                                        title = "Igor",
                                        subTitle = "QA TeamLead",
                                        versionCode = 1
                                )
                        ).shuffled().map {
                            apiMapper.map(it)
                        }
                )
        )
    } catch (exception: IOException) {
        flowOf(
                Response.Error(exception)
        )
    }

    override fun deleteUser(
            userId: Int
    ): Flow<Response<Unit, Exception>> = try {
        if (Random.nextBoolean()) {
            flowOf(Response.SuccessUnit)
        } else {
            flowOf(Response.Error(IOException("Unable to delete user = $userId")))
        }
    } catch (exception: IOException) {
        flowOf(Response.Error(exception))
    }
}
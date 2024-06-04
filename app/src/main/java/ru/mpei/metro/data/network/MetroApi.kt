package ru.mpei.metro.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.mpei.metro.domain.model.Branch
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.domain.model.Station

interface MetroApi {
    @GET("/metro/{cityId}")
    suspend fun getMetroGraph(@Path("cityId") cityId: String): Response<MetroGraph>

    @GET("/stations/{cityId}")
    suspend fun getStations(@Path("cityId") cityId: String): Response<List<Station>>

    @GET("/branches/{cityId}")
    suspend fun getBranches(@Path("cityId") cityId: String): Response<List<Branch>>
}

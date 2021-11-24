package com.mobile.data.usage.ApiService

import com.mobile.data.usage.DataSource.model.MobileDataRemote
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoints {
    @GET("/api/action/datastore_search")
    suspend fun getMobileDataUsage(
        @Query("resource_id") resource_id: String?,
        @Query("offset") offset: Int,
        @Query("limit") page: Int,
    ): MobileDataRemote?
}
package com.mobile.data.usage.DataSource.repository

import com.mobile.data.usage.ApiService.ApiEndpoints
import com.mobile.data.usage.DataSource.model.MobileDataRemote
import com.mobile.data.usage.Domain.repository.MobileDataSourceRepository
import retrofit2.Retrofit

class MobileDataSourceRepositoryImpl(private val retrofit: Retrofit): MobileDataSourceRepository {
    override suspend fun getMobileDataUsage(resourceId: String, offset: Int, limit: Int): MobileDataRemote? {
        return retrofit.create(ApiEndpoints::class.java).getMobileDataUsage(resourceId ,offset, limit)
    }
}
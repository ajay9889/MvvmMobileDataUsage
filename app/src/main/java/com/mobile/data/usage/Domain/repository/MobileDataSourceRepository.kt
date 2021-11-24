package com.mobile.data.usage.Domain.repository

import com.mobile.data.usage.DataSource.model.MobileDataRemote

interface MobileDataSourceRepository {
    suspend fun getMobileDataUsage(resourceId: String,offset: Int, limit: Int): MobileDataRemote?;
}
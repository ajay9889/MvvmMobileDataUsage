package com.mobile.data.usage.Domain.repository

import androidx.lifecycle.MutableLiveData
import com.mobile.data.usage.Presentation.ViewModel.HomeViewModel

interface MobileDataSourceRepository {
    suspend fun getMobileDataUsage(mState: MutableLiveData<HomeViewModel.RequestState>, resourceId: String, offset: Int, limit: Int)
}
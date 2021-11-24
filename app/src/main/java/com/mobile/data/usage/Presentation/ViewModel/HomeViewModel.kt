package com.mobile.data.usage.Presentation.ViewModel

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.mobile.data.usage.Core.networkutils.NetworkConnectivity
import com.mobile.data.usage.DataSource.model.Record
import com.mobile.data.usage.Domain.repository.MobileDataSourceRepository
import com.mobile.data.usage.MobileDataUsageApp
import org.json.JSONObject
import org.koin.java.KoinJavaComponent

class HomeViewModel (private val application: MobileDataUsageApp): AndroidViewModel(application) {
    val mobileDataSourceRepository = KoinJavaComponent.inject(MobileDataSourceRepository::class.java).value
    val PAGING_CONFIG = PagingConfig(
        pageSize = 10,
        prefetchDistance = 3,
        enablePlaceholders = true,
    )
    fun getDataUsageRecords() = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            HomePagingSource(application,mobileDataSourceRepository)
        }
    ).flowable.cachedIn(viewModelScope)


    class HomePagingSource(val context: Context, val mMobileDataSourceRepository :MobileDataSourceRepository): PagingSource<Int, Record>() {
        override fun getRefreshKey(state: PagingState<Int, Record>): Int? {
            return state.anchorPosition
        }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Record> {
            var LAST_PAGE = 1;
            var page = params.key ?: LAST_PAGE
            if(NetworkConnectivity.isNetworkConnected(context)){
                mMobileDataSourceRepository.getMobileDataUsage(
                    "a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
                    (page-1)*10,
                    page*10
                )?.let {
                    it.result?.records?.let {
                        return LoadResult.Page(
                            data = it,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (it.isNullOrEmpty()) null else page + 1
                        )
                    }
                }
            }

            return LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )
        }
    }
}
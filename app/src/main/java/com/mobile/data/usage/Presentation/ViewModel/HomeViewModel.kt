package com.mobile.data.usage.Presentation.ViewModel

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable

import com.mobile.data.usage.Core.networkutils.NetworkConnectivity
import com.mobile.data.usage.Database.Databasehelper
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.Domain.repository.MobileDataSourceRepository
import com.mobile.data.usage.MobileDataUsageApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.koin.java.KoinJavaComponent.inject

class HomeViewModel (private val application: MobileDataUsageApp): AndroidViewModel(application) {
    val mobileDataSourceRepository by inject(MobileDataSourceRepository::class.java)
    val mRequestState = MutableLiveData(RequestState.read_write)
    val dbInstance by inject(Databasehelper::class.java)
    init {
        CoroutineScope(Dispatchers.IO).launch {
            // delete and insert
            if(NetworkConnectivity.isNetworkConnected(application)) {
                // clear database table
                dbInstance.RoomDataAccessObejct().deleteTable()
                // re-insert all data
                mobileDataSourceRepository.getMobileDataUsage(
                    mRequestState,
                    "a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
                    0,
                    10
                )
            }else{
                mRequestState.postValue(RequestState.network_error)
            }
        }
    }
    val PAGING_CONFIG = PagingConfig(
        pageSize = 10,
        prefetchDistance = 3,
        enablePlaceholders = true,
    )
    fun getDataUsageRecords() = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            HomePagingSource(application,mobileDataSourceRepository,dbInstance)
        }
    ).flowable.cachedIn(viewModelScope)


    class HomePagingSource(val context: Context,
                           val mMobileDataSourceRepository :MobileDataSourceRepository,
                           val dbInstance: Databasehelper): PagingSource<Int, MobileDataDomain>() {

        override fun getRefreshKey(state: PagingState<Int, MobileDataDomain>): Int? {
            return state.anchorPosition
        }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MobileDataDomain> {

            return LoadResult.Page(
                data = dbInstance.RoomDataAccessObejct().getAllMobileDatas(),
                prevKey = null,
                nextKey = null
            )
        }
    }

    enum class RequestState{
        network_error,
        error,
        read_write,
        finished
    }
}
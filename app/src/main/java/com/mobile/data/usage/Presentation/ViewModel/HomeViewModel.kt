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
import com.mobile.data.usage.Database.Databasehelper
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.MobileDataUsageApp
import org.koin.java.KoinJavaComponent.inject

class HomeViewModel (private val application: MobileDataUsageApp): AndroidViewModel(application) {
    val mRequestState = MutableLiveData(RequestState.read_write)
    val dbInstance by inject(Databasehelper::class.java)

    val PAGING_CONFIG = PagingConfig(
        pageSize = 10,
        prefetchDistance = 3,
        enablePlaceholders = true,
    )
    fun getDataUsageRecords(selectedYear: String) = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            HomePagingSource(selectedYear ,application,dbInstance)
        }
    ).flowable.cachedIn(viewModelScope)


    class HomePagingSource(val selectedYear: String,
                           val context: Context,
                           val dbInstance: Databasehelper): PagingSource<Int, MobileDataDomain>() {

        override fun getRefreshKey(state: PagingState<Int, MobileDataDomain>): Int? {
            return state.anchorPosition
        }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MobileDataDomain> {

            return LoadResult.Page(
                data = if(selectedYear.length>0)
                    dbInstance.RoomDataAccessObejct().getAllMobileDatasByYear(selectedYear)
                else
                    dbInstance.RoomDataAccessObejct().getAllMobileDatas() ,
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
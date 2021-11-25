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
import com.mobile.data.usage.Database.Databasehelper
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.Domain.repository.MobileDataSourceRepository
import com.mobile.data.usage.MobileDataUsageApp
import org.koin.java.KoinJavaComponent

class YearViewModel(private  val application: MobileDataUsageApp): AndroidViewModel(application) {
    val dbInstance by KoinJavaComponent.inject(Databasehelper::class.java)

    fun getSelectedAllYearData()=dbInstance.RoomDataAccessObejct().getAllMobileDatas()
    fun getSelectedYearData(year: String)=dbInstance.RoomDataAccessObejct().getSelectedYearData(year)

    val PAGING_CONFIG = PagingConfig(
        pageSize = 10,
        prefetchDistance = 3,
        enablePlaceholders = true,
    )
    fun getSelectedYearRecords(year: String) = Pager(
        config = this.PAGING_CONFIG,
        pagingSourceFactory = {
            RecordPagingSource(year, application,dbInstance)
        }
    ).flowable.cachedIn(viewModelScope)
    class RecordPagingSource(val year: String,
                             val context: Context,
                           val dbInstance: Databasehelper): PagingSource<Int, MobileDataDomain>() {

        override fun getRefreshKey(state: PagingState<Int, MobileDataDomain>): Int? {
            return state.anchorPosition
        }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MobileDataDomain> {

            return LoadResult.Page(
                data = dbInstance.RoomDataAccessObejct().getSelectedYearData(year),
                prevKey = null,
                nextKey = null
            )
        }
    }
}
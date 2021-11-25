package com.mobile.data.usage.DataSource.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.mobile.data.usage.ApiService.ApiEndpoints
import com.mobile.data.usage.DataSource.model.MobileDataRemote
import com.mobile.data.usage.Database.Databasehelper
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.Domain.repository.MobileDataSourceRepository
import com.mobile.data.usage.Presentation.ViewModel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import retrofit2.Retrofit

class MobileDataSourceRepositoryImpl(private val retrofit: Retrofit): MobileDataSourceRepository {
    private val dbInstance by KoinJavaComponent.inject(Databasehelper::class.java)
    override suspend fun getMobileDataUsage(mState: MutableLiveData<HomeViewModel.RequestState>,
                                            resourceId: String,
                                             offset: Int,
                                            limit: Int){
         retrofit.create(ApiEndpoints::class.java).getMobileDataUsage(resourceId ,(offset+1)*10, limit)?.apply {
         this.result?.records?.let {

                 if(it.size<1){
                     mState.postValue(HomeViewModel.RequestState.finished)
                     return;
                 }
                CoroutineScope(Dispatchers.IO).launch {
                    // insert all data
                    it.map {
                        val itemRecords= MobileDataDomain(
                            _id = it._id!!,
                            year = it.quarter?.split("-")?.get(0)?.trim(),
                            quarter = it.quarter?.split("-")?.get(1)?.trim(),
                            volume_of_mobile_data = it.volume_of_mobile_data
                        )
                        dbInstance.RoomDataAccessObejct().insert(itemRecords)
                    }
                    // look for the next page data
                    getMobileDataUsage(mState,resourceId,(offset+1) , limit)
                }
            }
        }


    }
}
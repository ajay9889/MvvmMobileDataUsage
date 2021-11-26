package com.mobile.data.usage.WorkManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mobile.data.usage.Core.networkutils.NetworkConnectivity
import com.mobile.data.usage.Database.Databasehelper
import com.mobile.data.usage.Domain.repository.MobileDataSourceRepository
import com.mobile.data.usage.Presentation.ViewModel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SyncMobileDataUsage (private val appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    companion object{
        @JvmField
        var WORK_INFO_MANAGER_KEY: String ="SyncMobileDataUsage";
    }
    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            startSynceMobileDataFromEndPoint()
        }
        return Result.success()
    }

    private suspend fun startSynceMobileDataFromEndPoint() {
        if(NetworkConnectivity.isNetworkConnected(appContext)) {
            val mHomeViewModel by inject(HomeViewModel::class.java)
            val dbInstance by inject(Databasehelper::class.java)
            val mobileDataSourceRepository by inject(MobileDataSourceRepository::class.java)
            // clear database table
            dbInstance.RoomDataAccessObejct().deleteTable()
            // re-insert all data
            mobileDataSourceRepository.getMobileDataUsage(
                mHomeViewModel.mRequestState,
                "a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
                0,
                10
            )
        }
    }
}
package com.mobile.data.usage

import android.content.Context
import com.mobile.data.usage.Database.Databasehelper
import com.mobile.data.usage.Domain.repository.MobileDataSourceRepository
import com.mobile.data.usage.KoinDI.appModule
import com.mobile.data.usage.KoinDI.viewModelModule
import com.mobile.data.usage.Presentation.ViewModel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.java.KoinJavaComponent
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import org.robolectric.RuntimeEnvironment


@RunWith(MockitoJUnitRunner::class)
class MockApiUnitTests {
    @Mock
    private lateinit var mockContext: Context
    @Before
    fun setup() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(mockContext)
            modules(listOf(
                appModule, viewModelModule
            ))
        }
    }

    @Test
    fun init(){

        val dbHelper by KoinJavaComponent.inject(Databasehelper::class.java)
        val mHomeViewModel by KoinJavaComponent.inject(HomeViewModel::class.java)
        val mobileDataSourceRepository by KoinJavaComponent.inject(MobileDataSourceRepository::class.java)
        // re-insert all data
        CoroutineScope(Dispatchers.IO).launch {
            mobileDataSourceRepository.getMobileDataUsage(
                mHomeViewModel.mRequestState,
                "a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
                0,
                10
            ).apply {
                Assert.assertNotNull(dbHelper.RoomDataAccessObejct().getSelectedYearData(""))
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
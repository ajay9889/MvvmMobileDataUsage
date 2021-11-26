package com.mobile.data.usage

import android.content.Context
import com.mobile.data.usage.Database.Databasehelper
import com.mobile.data.usage.KoinDI.appModule
import com.mobile.data.usage.KoinDI.viewModelModule
import org.junit.After
import org.junit.Assert.assertNotNull
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


/**
 *  local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

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
    fun checkDB_isCorrect() {
        val dbInstance by KoinJavaComponent.inject(Databasehelper::class.java)
        assertNotNull(dbInstance.RoomDataAccessObejct().getSelectedYearData(""))
        assertNotNull(dbInstance.RoomDataAccessObejct().getSelectedYearData("2008"))
        assertNotNull(dbInstance.RoomDataAccessObejct().getSelectedYearData("2016"))
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
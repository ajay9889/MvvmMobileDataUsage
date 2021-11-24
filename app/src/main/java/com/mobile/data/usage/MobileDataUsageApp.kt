package com.mobile.data.usage
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.mobile.data.usage.KoinDI.appModule
import com.mobile.data.usage.KoinDI.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class MobileDataUsageApp : MultiDexApplication() {
    private val modules = listOf(
        appModule, viewModelModule
    )
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MobileDataUsageApp)
            modules(modules)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun resetKoin() {
        stopKoin()
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}
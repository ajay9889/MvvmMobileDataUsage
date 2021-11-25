package com.mobile.data.usage.KoinDI
import com.mobile.data.usage.BuildConfig
import com.mobile.data.usage.Core.networkutils.CacheInterceptor
import com.mobile.data.usage.Core.networkutils.OnlineCacheInterceptor
import com.mobile.data.usage.DataSource.repository.MobileDataSourceRepositoryImpl
import com.mobile.data.usage.Database.Databasehelper
import com.mobile.data.usage.Domain.repository.MobileDataSourceRepository
import com.mobile.data.usage.MobileDataUsageApp
import com.mobile.data.usage.Presentation.ViewModel.HomeViewModel
import com.mobile.data.usage.Presentation.ViewModel.YearViewModel
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        Databasehelper.getDatabase(androidContext())
    }
    single{
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }
            }
            .addInterceptor(CacheInterceptor(androidContext()))
            .addNetworkInterceptor(OnlineCacheInterceptor())
            .cache(Cache(File(androidContext().cacheDir, "api"), 1024 * 1024 * 1024L)).build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_DOMAIN)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    single<MobileDataSourceRepository> { MobileDataSourceRepositoryImpl(get()) }

}

val viewModelModule = module {
    viewModel{
        HomeViewModel(androidApplication() as MobileDataUsageApp)
    }
    viewModel{
        YearViewModel(androidApplication() as MobileDataUsageApp)
    }

}
package com.example.videoapp.app

import android.app.Application
import com.example.data.di.dataModule
import com.example.videoapp.di.appModule
import com.example.videoapp.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.GlobalContext.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}
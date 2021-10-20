package com.missclickads.cleaner

import android.app.Application
import com.missclickads.cleaner.di.appModule
import com.missclickads.cleaner.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(appModule, dataModule)
        }
    }
}
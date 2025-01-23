package com.example.inqu1sitor.rssreader.androidApp

import android.app.Application
import com.example.inqu1sitor.rssreader.androidApp.sync.RefreshWorker
import com.example.inqu1sitor.rssreader.app.FeedStore
import com.example.inqu1sitor.rssreader.core.RssReader
import com.example.inqu1sitor.rssreader.core.create
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        launchBackgroundSync()
    }

    private val appModule = module {
        single { RssReader.create(get(),
            com.example.inqu1sitor.rssreader.androidApp.BuildConfig.DEBUG
        ) }
        single { FeedStore(get()) }
    }

    private fun initKoin() {
        startKoin {
            if (com.example.inqu1sitor.rssreader.androidApp.BuildConfig.DEBUG) androidLogger(Level.ERROR)

            androidContext(this@App)
            modules(appModule)
        }
    }

    private fun launchBackgroundSync() {
        RefreshWorker.enqueue(this)
    }
}
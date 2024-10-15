package com.malakiapps.catfacts

import android.app.Application
import com.malakiapps.catfacts.common.di.initKoin
import org.koin.android.ext.koin.androidContext

class CatFactsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CatFactsApplication)
        }
    }
}
package com.malakiapps.catfacts

import android.app.Application
import com.malakiapps.catfacts.common.di.initKoin

class CatFactsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
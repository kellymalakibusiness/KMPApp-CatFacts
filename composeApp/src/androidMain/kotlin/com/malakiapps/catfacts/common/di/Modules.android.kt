package com.malakiapps.catfacts.common.di

import com.malakiapps.catfacts.data.common.GetDataStorePath
import com.malakiapps.catfacts.data.common.LinkOpener
import com.malakiapps.catfacts.domain.ImageSelector
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        OkHttp.create()
    }
    single {
        GetDataStorePath(get()).getThePath()
    }
    single {
        LinkOpener(get())
    }
    single {
        ImageSelector(get())
    }
}
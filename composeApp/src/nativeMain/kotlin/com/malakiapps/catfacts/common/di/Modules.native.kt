package com.malakiapps.catfacts.common.di

import com.malakiapps.catfacts.data.common.GetDataStorePath
import com.malakiapps.catfacts.data.common.LinkOpener
import com.malakiapps.catfacts.domain.ImageSelector
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        Darwin.create()
    }
    single {
        GetDataStorePath().getThePath()
    }
    single {
        LinkOpener()
    }
    single {
        ImageSelector()
    }
}
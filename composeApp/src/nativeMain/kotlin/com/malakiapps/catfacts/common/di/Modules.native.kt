package com.malakiapps.catfacts.common.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        Darwin.create()
    }
}
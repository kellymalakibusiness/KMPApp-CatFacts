package com.malakiapps.catfacts.common.di

import com.malakiapps.catfacts.data.common.createHttpClient
import com.malakiapps.catfacts.data.common.initializeRealmDB
import com.malakiapps.catfacts.data.fact.DummyFactRepository
import com.malakiapps.catfacts.data.fact.FactRepository
import com.malakiapps.catfacts.data.fact.HttpFactRepository
import com.malakiapps.catfacts.data.image.CatImageRepository
import com.malakiapps.catfacts.data.image.DummyImageRepository
import com.malakiapps.catfacts.data.image.HttpCatImageRepository
import com.malakiapps.catfacts.data.localDatabase.LocalStorageRepository
import com.malakiapps.catfacts.data.localDatabase.RealmLocalStorageRepository
import com.malakiapps.catfacts.domain.MainViewModel
import com.malakiapps.catfacts.domain.useCases.QueryFactsUseCase
import io.ktor.client.engine.HttpClientEngine
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single {
        createHttpClient(get<HttpClientEngine>())
    }

    single {
        initializeRealmDB()
    }
    singleOf(::RealmLocalStorageRepository).bind<LocalStorageRepository>()

    //singleOf(::HttpFactRepository).bind<FactRepository>()
    //singleOf(::HttpCatImageRepository).bind<CatImageRepository>()

    singleOf(::DummyFactRepository).bind<FactRepository>()
    singleOf(::DummyImageRepository).bind<CatImageRepository>()

    singleOf(::QueryFactsUseCase)
    viewModelOf(::MainViewModel)
}
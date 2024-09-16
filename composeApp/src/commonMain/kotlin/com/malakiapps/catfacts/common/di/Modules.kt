package com.malakiapps.catfacts.common.di

import com.malakiapps.catfacts.data.fact.FactRepository
import com.malakiapps.catfacts.data.fact.TestFactRepository
import com.malakiapps.catfacts.domain.MainViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::TestFactRepository).bind<FactRepository>()
    viewModelOf(::MainViewModel)
}
package com.apiguave.newTins.ui.di


import com.apiguave.newTins.di.dataModule
import org.koin.dsl.module

val appModule = module {
    includes(dataModule)
    includes(presentationModule)
}
package com.example.nimble.koin

import com.example.nimble.datastore.AuthStorageRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { AuthStorageRepository(androidContext()) }
}

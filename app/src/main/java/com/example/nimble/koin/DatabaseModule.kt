package com.example.nimble.koin

import androidx.room.Room
import com.example.nimble.database.NIMBLE_DATABASE_NAME
import com.example.nimble.database.NimbleDatabase
import com.example.nimble.database.repositories.AuthDatabaseRepository
import com.example.nimble.database.repositories.SurveysDatabaseRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass =  NimbleDatabase::class.java,
            name = NIMBLE_DATABASE_NAME
        ).build()
    }

    single { AuthDatabaseRepository(get<NimbleDatabase>().userTokenDao()) }
    single { SurveysDatabaseRepository(get<NimbleDatabase>().surveyDao()) }

}

package com.example.nimble.datastore

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = NimbleDataStore.DATASTORE_NAME)

object NimbleDataStore {

    const val DATASTORE_NAME = "nimble_datastore"
    val USER_TOKEN_KEY = stringPreferencesKey("user_token")

}

package com.example.nimble.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.nimble.datastore.NimbleDataStore.USER_TOKEN_KEY
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AuthStorageRepository(private val dataStore: DataStore<Preferences>) {

    fun getAccessToken() = flow {
        emit(dataStore.data.map {
            it[USER_TOKEN_KEY]
        }.first())
    }

    suspend fun saveUserAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    suspend fun deleteUserAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN_KEY)
        }
    }
}

package com.example.nimble.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.nimble.datastore.NimbleDataStore.USER_TOKEN_KEY
import kotlinx.coroutines.flow.map

class AuthStorageRepository(private val context: Context) {

    fun getAccessToken() = context.dataStore.data.map {
        it[USER_TOKEN_KEY]
    }

    suspend fun saveUserAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    suspend fun deleteUserAccessToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN_KEY)
        }
    }
}

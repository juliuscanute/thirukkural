package com.juliuscanute.tkural.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class ThirukuralDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    private val KURAL_NUMBER = intPreferencesKey("kural_number")

    suspend fun saveKuralNumber(kuralNumber: Int) {
        context.dataStore.edit { preferences ->
            preferences[KURAL_NUMBER] = kuralNumber
        }
    }

    fun getKuralNumber(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[KURAL_NUMBER] ?: 1
        }
    }
}
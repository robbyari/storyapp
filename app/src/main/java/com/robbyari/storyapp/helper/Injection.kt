package com.robbyari.storyapp.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.robbyari.storyapp.repository.data.StoryRepository
import com.robbyari.storyapp.repository.local.datastore.UserPreference
import com.robbyari.storyapp.repository.local.room.StoryDatabase
import com.robbyari.storyapp.repository.remote.retrofit.ApiConfig

object Injection {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return StoryRepository(database, apiService, pref)
    }
}
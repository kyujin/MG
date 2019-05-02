package com.example.mg.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsController(applicationContext: Context) {

    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("meet_group_tweet_shared_prefs", Context.MODE_PRIVATE)

    fun writeToSharedPrefs(token: String, key: String) {
        with (preferences.edit()) {
            putString(key, token)
            commit()
        }
    }

    fun readFromSharedPrefs(key: String): String? {
        return preferences.getString(key, null)
    }

    companion object {

        @Volatile
        private var INSTANCE: SharedPrefsController? = null

        fun getInstance(context: Context): SharedPrefsController {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: SharedPrefsController(context).also { INSTANCE = it }
            }
        }
    }
}
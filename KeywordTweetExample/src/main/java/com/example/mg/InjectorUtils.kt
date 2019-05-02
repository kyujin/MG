package com.example.mg

import android.content.Context
import com.example.mg.preferences.SharedPrefsController
import com.example.mg.repository.TweetRepository
import com.example.mg.viewmodel.SearchViewModelFactory

object InjectorUtils {

    private fun getTweetRepository(context: Context): TweetRepository {
        return TweetRepository.getInstance(SharedPrefsController.getInstance(context))
    }

    fun provideTweetViewModelFactory(context: Context): SearchViewModelFactory {
        val repository = getTweetRepository(context)
        return SearchViewModelFactory(repository)
    }
}
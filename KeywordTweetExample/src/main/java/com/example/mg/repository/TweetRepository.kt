package com.example.mg.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mg.network.SearchService
import com.example.mg.data.Tweet
import com.example.mg.preferences.SharedPrefsController
import com.example.neuroflow.network.NetworkHelper

class TweetRepository(val sharedPrefsController: SharedPrefsController,
                      val service: SearchService = NetworkHelper.service) {

    fun loadTweets(keyword: String): LiveData<PagedList<Tweet>> {
        val config = PagedList.Config.Builder().setPageSize(10).build()
        val sourceFactory = TweetDataSourceFactory(service, keyword, sharedPrefsController)
        return LivePagedListBuilder<String, Tweet>(sourceFactory, config).build()
    }

    companion object {

        @Volatile
        private var INSTANCE: TweetRepository? = null

        fun getInstance(sharedPrefsController: SharedPrefsController): TweetRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: TweetRepository(sharedPrefsController).also { INSTANCE = it }
            }
        }
    }
}


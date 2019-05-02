package com.example.mg.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mg.network.SearchService
import com.example.mg.data.Tweet
import com.example.mg.preferences.SharedPrefsController

class TweetDataSourceFactory(val service: SearchService,
                             val keyword: String,
                             val sharedPrefsController: SharedPrefsController) : DataSource.Factory<String, Tweet>() {
    val sourceLiveData = MutableLiveData<TweetDataSource>()
    override fun create(): DataSource<String, Tweet> {
        val source = TweetDataSource(service, keyword, sharedPrefsController)
        sourceLiveData.postValue(source)
        return source
    }
}
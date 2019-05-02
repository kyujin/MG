package com.example.mg.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.mg.Utils
import com.example.mg.data.Tweet
import com.example.mg.network.SearchService
import com.example.mg.preferences.SharedPrefsController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TweetDataSource(val service: SearchService,
                      val keyword: String,
                      val sharedPrefsController: SharedPrefsController)
    : PageKeyedDataSource<String, Tweet>(), CoroutineScope {

    companion object {
        const val AUTH_KEY: String = "VXjfndVdSymvWQpTgSB6b67zD:Vz3Z4A9T5TrzPAjjQCKg1EJ1PJG4mewV3fzC8ocuGDGcPemAX5"
    }

    private val TAG = TweetDataSource::class.java.simpleName

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    var auth: String? = null

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Tweet>) {
        Log.d(TAG, "loadAfter")
        auth = sharedPrefsController.readFromSharedPrefs("bearer_token") ?: getToken()
        val response = service.getTweet(keyword, "Bearer $auth").execute()
        if (response.isSuccessful) {
            val nextResults = response.body()!!.search_metadata.next_results
            val items = response.body()!!.statuses
            callback.onResult(items, null, nextResults)
        } else {
            Log.d(TAG, "Network Error with error code $response.code")
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Tweet>) {
        Log.d(TAG, "loadAfter")
        launch {
            val response = service.getTweetAfter("/1.1/search/tweets.json${params.key}", "Bearer $auth").await()
            if (response.isSuccessful) {
                val nextReuslts = response.body()!!.search_metadata.next_results
                val items = response.body()!!.statuses
                callback.onResult(items, nextReuslts)
            } else {
                Log.d(TAG, "Network Error with error code $response.code")
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Tweet>) {
        // nothing to load before
    }

    fun getToken(): String? {
        val response = service.getToken("Basic ${Utils.getBase64String(AUTH_KEY)}", "client_credentials").execute()
        return if (response.isSuccessful) {
            val token = response.body()!!.access_token
            sharedPrefsController.writeToSharedPrefs(token, "bearer_token")
            token
        } else {
            Log.d(TAG,"Network Error with error code $response.code")
            null
        }
    }
}
package com.example.mg.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.mg.data.Tweet
import com.example.mg.repository.TweetRepository
import java.util.*

class SearchViewModel(val tweetRepository: TweetRepository) : ViewModel() {

    private val keyword =  MutableLiveData<String>()

    val tweets: LiveData<PagedList<Tweet>> = Transformations.switchMap(keyword) {
        tweetRepository.loadTweets(it)
    }
    
    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == keyword.value) {
            return
        }
        keyword.value = input
    }
}
package com.example.neuroflow.network

import com.example.mg.network.SearchService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {

    const val BASE_URL = "https://api.twitter.com"
    const val CONSUMER_KEY = "VXjfndVdSymvWQpTgSB6b67zD"
    const val CONSUMER_SECRET = "Vz3Z4A9T5TrzPAjjQCKg1EJ1PJG4mewV3fzC8ocuGDGcPemAX5"

    fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val service: SearchService = retrofit().create(SearchService::class.java)

}
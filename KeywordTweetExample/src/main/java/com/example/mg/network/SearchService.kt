package com.example.mg.network

import com.example.mg.data.SearchResponse
import com.example.mg.data.TokenResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface SearchService {

    @GET("/1.1/search/tweets.json")
    fun getTweet(@Query("q") keyword: String,
                 @Header("Authorization") authorization: String) : Call<SearchResponse>

    @GET
    fun getTweetAfter(@Url url: String?,
                      @Header("Authorization") authorization: String) : Deferred<Response<SearchResponse>>

    @FormUrlEncoded
    @POST("/oauth2/token")
    fun getToken(@Header("Authorization") authorization: String,
                 @Field("grant_type") grantType: String) : Call<TokenResponse>
}
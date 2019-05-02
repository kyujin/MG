package com.example.mg.data

data class SearchResponse(
    val search_metadata: SearchMetadata,
    val statuses: List<Tweet>
)
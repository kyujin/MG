package com.example.mg.data

data class Media(
    val display_url: String,
    val expanded_url: String,
    val id: Long,
    val id_str: String,
    val indices: List<Int>,
    val media_url: String,
    val media_url_https: String,
    val sizes: Sizes,
    val source_status_id: Long,
    val source_status_id_str: String,
    val source_user_id: Long,
    val source_user_id_str: String,
    val type: String,
    val url: String
)
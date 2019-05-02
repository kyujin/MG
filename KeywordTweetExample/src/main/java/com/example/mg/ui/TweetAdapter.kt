package com.example.mg.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mg.R
import com.example.mg.data.Tweet


class TweetAdapter : PagedListAdapter<Tweet, TweetAdapter.TweetViewHolder>(REPO_COMPARATOR) {

    class TweetViewHolder(textView: View) : RecyclerView.ViewHolder(textView) {
        val userNameTextView: TextView = textView.findViewById(R.id.userName)
        val tweetTextTextView: TextView = textView.findViewById(R.id.tweetText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item, parent, false)
        return TweetViewHolder(textView)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val tweet = getItem(position)
        holder.userNameTextView.text = tweet?.user?.name
        holder.tweetTextTextView.text = tweet?.text
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Tweet>() {
            override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean =
                oldItem == newItem
        }
    }
}
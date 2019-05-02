package com.example.mg.ui

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mg.InjectorUtils
import com.example.mg.R
import com.example.mg.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var tweetAdapter: TweetAdapter
    private lateinit var editText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = InjectorUtils.provideTweetViewModelFactory(requireActivity())
        searchViewModel = ViewModelProviders.of(requireActivity(), factory).get(SearchViewModel::class.java)
        tweetAdapter = TweetAdapter()
        requireActivity().findViewById<RecyclerView>(R.id.tweet_list).apply {
            adapter = tweetAdapter
        }

        subscribeUi(tweetAdapter)
        editText = requireActivity().findViewById<EditText>(R.id.input).apply {
            setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchKeyword(v)
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun subscribeUi(adapter: TweetAdapter) {
        searchViewModel.tweets.observe(this, Observer { tweets ->
            tweets?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun searchKeyword(v: View) {
        val query = editText.text.toString()
        // Dismiss keyboard
        dismissKeyboard(v.windowToken)
        searchViewModel.setQuery(query)
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}
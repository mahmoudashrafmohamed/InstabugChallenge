package com.mahmoudashraf.instabugchallenge.words.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudashraf.instabugchallenge.R
import com.mahmoudashraf.instabugchallenge.core.adroidextensions.observe
import com.mahmoudashraf.instabugchallenge.words.adapter.WordsAdapter
import com.mahmoudashraf.instabugchallenge.words.model.WordUIModel
import com.mahmoudashraf.instabugchallenge.words.viewmodel.WordsScreenState
import com.mahmoudashraf.instabugchallenge.words.viewmodel.WordsViewModel

class WordsFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var emptyView : TextView
    private lateinit var wordsRecycler : RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var sortBtn: ImageButton

    private val viewModelProvider: (ViewModelStoreOwner) -> WordsViewModel = {
        ViewModelProvider(it)[WordsViewModel::class.java]
    }
    private val viewModel by lazy { viewModelProvider(this) }

    private val wordsAdapter by lazy { WordsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_words, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe(viewModel.screenState, ::onScreenStateChange)
        viewModel.getWords()
    }

    private fun onScreenStateChange(state: WordsScreenState) {
        when (state) {
            is WordsScreenState.Loading -> showLoading()
            is WordsScreenState.Success -> handleSuccessState(state.wordsList)
            is WordsScreenState.Error ->  handleErrorState()
            is WordsScreenState.Empty -> handleEmptyState()
        }
    }

    private fun handleEmptyState() {
        showEmptyView()
        hideWordsRecyclerView()
    }

    private fun hideWordsRecyclerView() {
        wordsRecycler.visibility = View.GONE
    }

    private fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
    }

    private fun handleErrorState() {
       Toast.makeText(context,getString(R.string.something_wrong_error_msg),Toast.LENGTH_LONG).show()
    }

    private fun handleSuccessState(wordsList: List<WordUIModel>) {
        hideLoading()
        hideEmptyView()
        showWordsRecyclerView()
        bindRecyclerData(wordsList)
    }

    private fun hideEmptyView() {
        emptyView.visibility = View.GONE
    }

    private fun showWordsRecyclerView() {
        wordsRecycler.visibility = View.VISIBLE
    }

    private fun bindRecyclerData(wordsList: List<WordUIModel>) {
        wordsAdapter.submitList(wordsList)
        wordsRecycler.post { wordsRecycler.scrollToPosition(0) }
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun initView() {
        view?.let {
            progressBar =  it.findViewById(R.id.progress_bar)
            emptyView = it.findViewById(R.id.tv_empty_data)
            wordsRecycler = it.findViewById(R.id.rv_words)
            searchView = it.findViewById(R.id.sv_main)
            sortBtn = it.findViewById(R.id.img_sort)
        } ?: return
        setupWordsRecyclerView(wordsRecycler)
        setupSearchView()
        setupSortClickListener()
    }

    private fun setupSortClickListener() {
        sortBtn.setOnClickListener {
            viewModel.toggleSort()
        }
    }

    private fun setupSearchView() {
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterWordsByName(newText?:"")
                return true
            }
        })
    }

    private fun setupWordsRecyclerView(wordsRecycler : RecyclerView) {
        wordsRecycler.adapter = wordsAdapter
    }

}
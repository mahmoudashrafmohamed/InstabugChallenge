package com.mahmoudashraf.instabugchallenge.words.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudashraf.domain.usecases.GetWordsUseCase
import com.mahmoudashraf.instabugchallenge.core.executor.AppExecutors
import com.mahmoudashraf.instabugchallenge.core.executor.AppExecutorsManager
import com.mahmoudashraf.instabugchallenge.words.mappers.mapToWordsListUIModel
import com.mahmoudashraf.instabugchallenge.words.model.WordUIModel

class WordsViewModel(
    private val getWordsUseCase: GetWordsUseCase,
    private val executors: AppExecutorsManager = AppExecutors.instance
) : ViewModel() {

    val screenState by lazy { MutableLiveData<WordsScreenState>() }

    fun getWords() {
        screenState.postValue(WordsScreenState.Loading)
        executors.networkIO.execute {
            try {
                val words = getWordsUseCase()
                val wordsUIModelsList = words.mapToWordsListUIModel()
                if (wordsUIModelsList.isEmpty())
                    screenState.postValue(WordsScreenState.Empty)
                else
                    screenState.postValue(WordsScreenState.Success(wordsUIModelsList))
            } catch (ex: Exception) {
                ex.printStackTrace()
                screenState.postValue(WordsScreenState.Error(ex))
            }
        }
    }
}

sealed class WordsScreenState {
    object Loading : WordsScreenState()
    data class Success(val wordsList: List<WordUIModel>) : WordsScreenState()
    class Error(val ex: Exception) : WordsScreenState()
    object Empty : WordsScreenState()
}

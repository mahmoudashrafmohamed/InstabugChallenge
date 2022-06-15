package com.mahmoudashraf.instabugchallenge.words.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudashraf.data.repositories.WordsRepositoryImpl
import com.mahmoudashraf.domain.usecases.GetWordsUseCase
import com.mahmoudashraf.instabugchallenge.core.executor.AppExecutors
import com.mahmoudashraf.instabugchallenge.core.executor.AppExecutorsManager
import com.mahmoudashraf.instabugchallenge.words.mappers.mapToWordsListUIModel
import com.mahmoudashraf.instabugchallenge.words.model.WordUIModel

enum class SortType {ASC, DESC}
class WordsViewModel(
    private val getWordsUseCase: GetWordsUseCase = GetWordsUseCase(wordsRepository = WordsRepositoryImpl()),
    private val executors: AppExecutorsManager = AppExecutors.instance,
) : ViewModel() {

    val screenState by lazy { MutableLiveData<WordsScreenState>() }
    private val cachedWordList = mutableListOf<WordUIModel>()
    private var sortType = SortType.ASC.name

    fun getWords() {
        screenState.postValue(WordsScreenState.Loading)
        executors.networkIO.execute {
            try {
                val words = getWordsUseCase()
                val wordsUIModelsList = words.mapToWordsListUIModel()
                setInMemoryCachedList(wordsUIModelsList)
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

    private fun setInMemoryCachedList(wordsUIModelsList: List<WordUIModel>) {
        cachedWordList.clear()
        cachedWordList.addAll(wordsUIModelsList)
    }

    fun filterWordsByName(word: String) {
        val filteredList = cachedWordList.filter { it.name.contains(word,true) }
        if (filteredList.isEmpty())
            screenState.postValue(WordsScreenState.Empty)
        else
            screenState.postValue(WordsScreenState.Success(filteredList))
    }

    fun toggleSort() {
        if (sortType==SortType.ASC.name){
            sortType= SortType.DESC.name
            val sortedList = cachedWordList.sortedBy { it.count }.reversed()
            emitSortedListState(sortedList)
        }
        else {
            sortType= SortType.ASC.name
            val sortedList = cachedWordList.sortedBy { it.count }
            emitSortedListState(sortedList)
        }
    }

    private fun emitSortedListState(sortedList: List<WordUIModel>) {
        if (sortedList.isEmpty())
            screenState.postValue(WordsScreenState.Empty)
        else
            screenState.postValue(WordsScreenState.Success(sortedList))
    }

}

sealed class WordsScreenState {
    object Loading : WordsScreenState()
    data class Success(val wordsList: List<WordUIModel>) : WordsScreenState()
    class Error(val ex: Exception) : WordsScreenState()
    object Empty : WordsScreenState()
}

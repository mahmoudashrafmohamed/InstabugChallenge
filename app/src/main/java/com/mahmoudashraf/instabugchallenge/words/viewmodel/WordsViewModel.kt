package com.mahmoudashraf.instabugchallenge.words.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudashraf.data.repositories.WordsRepositoryImpl
import com.mahmoudashraf.domain.usecases.GetWordsUseCase
import com.mahmoudashraf.core.executor.AppExecutors
import com.mahmoudashraf.instabugchallenge.words.mappers.mapToWordsListUIModel
import com.mahmoudashraf.instabugchallenge.words.model.WordUIModel
import java.util.concurrent.Executor

enum class SortType {ASC, DESC}
class WordsViewModel(
    private val getWordsUseCase: GetWordsUseCase = GetWordsUseCase(wordsRepository = WordsRepositoryImpl()),
    private val executor: Executor = AppExecutors.instance.networkIO,
    private val cachedWordList : MutableList<WordUIModel> = mutableListOf(),
    private var sortType :String = SortType.ASC.name
) : ViewModel() {

    val screenState by lazy { MutableLiveData<WordsScreenState>() }

    fun getWords() {
        screenState.postValue(WordsScreenState.Loading)
        executor.execute {
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

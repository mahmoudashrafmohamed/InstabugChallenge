package com.mahmoudashraf.instabugchallenge.words.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mahmoudashraf.data.repositories.WordsRepositoryImpl
import com.mahmoudashraf.domain.usecases.GetWordsUseCase
import com.mahmoudashraf.instabugchallenge.words.mappers.mapToWordsListUIModel
import com.mahmoudashraf.instabugchallenge.words.model.WordUIModel
import com.mahmoudashraf.local.WordsLocalDataSource
import com.mahmoudashraf.remote.WordsRemoteDataSource
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executor


class WordsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `getWords() when invoke then  emit SuccessState()`() {
        // arrange
        val words = listOf("android", "test")
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                return words
            }
        }

        val localDataSource = object : WordsLocalDataSource {
            override fun getWords(): List<String> {
                return words
            }

            override fun saveWords(words: List<String>) {}
        }

        class CurrentThreadExecutor : Executor {
            override fun execute(r: Runnable) {
                r.run()
            }
        }

        val currentThreadExecutor = CurrentThreadExecutor()
        val wordsRepositoryImpl =
            WordsRepositoryImpl(remoteDataSource, localDataSource, isInternetAvailable = true)

        val getWordsUseCase = GetWordsUseCase(wordsRepositoryImpl)

        val viewModel = WordsViewModel(getWordsUseCase, currentThreadExecutor)
        // act
        viewModel.getWords()
        // assert
        Assert.assertEquals(
            WordsScreenState.Success(words.mapToWordsListUIModel()),
            viewModel.screenState.value
        )

    }

    @Test
    fun `getWords() when invoke then  emit EmptyState()`() {
        // arrange
        val words = listOf<String>()
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                return words
            }
        }

        val localDataSource = object : WordsLocalDataSource {
            override fun getWords(): List<String> {
                return words
            }

            override fun saveWords(words: List<String>) {}
        }

        class CurrentThreadExecutor : Executor {
            override fun execute(r: Runnable) {
                r.run()
            }
        }

        val currentThreadExecutor = CurrentThreadExecutor()
        val wordsRepositoryImpl =
            WordsRepositoryImpl(remoteDataSource, localDataSource, isInternetAvailable = true)

        val getWordsUseCase = GetWordsUseCase(wordsRepositoryImpl)

        val viewModel = WordsViewModel(getWordsUseCase, currentThreadExecutor)
        // act
        viewModel.getWords()
        // assert
        Assert.assertEquals(WordsScreenState.Empty, viewModel.screenState.value)

    }

    @Test
    fun `getWords() when invoke then  emit ErrorState()`() {
        // arrange
        val words = listOf<String>()
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                throw Exception("something went wrong.")
            }
        }

        val localDataSource = object : WordsLocalDataSource {
            override fun getWords(): List<String> {
                return words
            }

            override fun saveWords(words: List<String>) {}
        }

        class CurrentThreadExecutor : Executor {
            override fun execute(r: Runnable) {
                r.run()
            }
        }

        val currentThreadExecutor = CurrentThreadExecutor()
        val wordsRepositoryImpl =
            WordsRepositoryImpl(remoteDataSource, localDataSource, isInternetAvailable = true)

        val getWordsUseCase = GetWordsUseCase(wordsRepositoryImpl)

        val viewModel = WordsViewModel(getWordsUseCase, currentThreadExecutor)
        // act
        viewModel.getWords()
        // assert
        Assert.assertTrue(viewModel.screenState.value is WordsScreenState.Error)

    }

    @Test
    fun `filterByName() when invoke then emit Success with filtered data()`() {
        // arrange
        val words = listOf<String>()
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                throw Exception("something went wrong.")
            }
        }

        val localDataSource = object : WordsLocalDataSource {
            override fun getWords(): List<String> {
                return words
            }

            override fun saveWords(words: List<String>) {}
        }

        class CurrentThreadExecutor : Executor {
            override fun execute(r: Runnable) {
                r.run()
            }
        }

        val currentThreadExecutor = CurrentThreadExecutor()
        val wordsRepositoryImpl =
            WordsRepositoryImpl(remoteDataSource, localDataSource, isInternetAvailable = true)

        val getWordsUseCase = GetWordsUseCase(wordsRepositoryImpl)

        val cachedWordsList = mutableListOf(
            WordUIModel("io", 1),
            WordUIModel("ios1", 1),
            WordUIModel("android1", 1)
        )

        val viewModel = WordsViewModel(getWordsUseCase, currentThreadExecutor, cachedWordsList)
        // act
        viewModel.filterWordsByName("ios")
        // assert
        Assert.assertTrue(viewModel.screenState.value is WordsScreenState.Success)
        Assert.assertEquals(
            WordsScreenState.Success(listOf(WordUIModel("ios1", 1))),
            viewModel.screenState.value
        )

    }

    @Test
    fun `toggleSort() when invoke then emit Success with sorted data()`() {
        // arrange
        val words = listOf<String>()
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                throw Exception("something went wrong.")
            }
        }

        val localDataSource = object : WordsLocalDataSource {
            override fun getWords(): List<String> {
                return words
            }

            override fun saveWords(words: List<String>) {}
        }

        class CurrentThreadExecutor : Executor {
            override fun execute(r: Runnable) {
                r.run()
            }
        }

        val currentThreadExecutor = CurrentThreadExecutor()
        val wordsRepositoryImpl =
            WordsRepositoryImpl(remoteDataSource, localDataSource, isInternetAvailable = true)

        val getWordsUseCase = GetWordsUseCase(wordsRepositoryImpl)

        val cachedWordsList = mutableListOf(
            WordUIModel("io", 3),
            WordUIModel("ios1", 1),
            WordUIModel("android1", 2)
        )
        val sortType = SortType.DESC.name

        val viewModel = WordsViewModel(
            getWordsUseCase, currentThreadExecutor, cachedWordsList,
            sortType
        )
        // act
        viewModel.toggleSort()
        // assert
        val sortedList = mutableListOf(
            WordUIModel("ios1", 1),
            WordUIModel("android1", 2),
            WordUIModel("io", 3)
        )
        Assert.assertEquals(WordsScreenState.Success(sortedList), viewModel.screenState.value)
    }


}
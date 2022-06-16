package com.mahmoudashraf.data.repositories

import com.mahmoudashraf.core.executor.AppExecutors
import com.mahmoudashraf.core.extensions.isInternetAvailable
import com.mahmoudashraf.domain.repositories.WordsRepository
import com.mahmoudashraf.local.WordsLocalDataSource
import com.mahmoudashraf.local.WordsLocalDataSourceImpl
import com.mahmoudashraf.remote.WordsAPI
import com.mahmoudashraf.remote.WordsRemoteDataSource
import java.util.concurrent.Executor

class WordsRepositoryImpl(
    private val wordsRemoteDataSource: WordsRemoteDataSource = WordsAPI(),
    private val wordsLocalDataSource: WordsLocalDataSource = WordsLocalDataSourceImpl(),
    private val isInternetAvailable : Boolean = isInternetAvailable(),
    private val executor: Executor = AppExecutors.instance.diskIO
) : WordsRepository {
    override fun getWords(): List<String> {
        return if (isInternetAvailable) {
            val remoteResponse = wordsRemoteDataSource.getWords()
            executor.execute {
                wordsLocalDataSource.saveWords(remoteResponse)
            }
            remoteResponse
        } else {
            val localResponse = wordsLocalDataSource.getWords()
            localResponse
        }
    }
}
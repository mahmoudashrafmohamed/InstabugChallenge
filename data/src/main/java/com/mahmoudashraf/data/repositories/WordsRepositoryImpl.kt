package com.mahmoudashraf.data.repositories

import com.mahmoudashraf.core.extensions.isInternetAvailable
import com.mahmoudashraf.data.sources.local.WordsLocalDataSource
import com.mahmoudashraf.data.sources.local.WordsLocalDataSourceImpl
import com.mahmoudashraf.data.sources.remote.WordsAPI
import com.mahmoudashraf.data.sources.remote.WordsRemoteDataSource
import com.mahmoudashraf.domain.repositories.WordsRepository

class WordsRepositoryImpl(
    private val wordsRemoteDataSource: WordsRemoteDataSource = WordsAPI(),
    private val wordsLocalDataSource: WordsLocalDataSource = WordsLocalDataSourceImpl()
) : WordsRepository {
    override fun getWords(): List<String> {
        return if (isInternetAvailable()) {
            val remoteResponse = wordsRemoteDataSource.getWords()
            wordsLocalDataSource.saveWords(remoteResponse)
            remoteResponse
        } else {
            val localResponse = wordsLocalDataSource.getWords()
            localResponse
        }
    }
}
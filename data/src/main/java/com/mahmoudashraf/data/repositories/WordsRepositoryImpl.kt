package com.mahmoudashraf.data.repositories

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
        val words =  wordsRemoteDataSource.getWords()
        wordsLocalDataSource.saveWords(words)
        return words
    }
}
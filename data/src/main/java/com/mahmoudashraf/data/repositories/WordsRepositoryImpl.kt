package com.mahmoudashraf.data.repositories

import com.mahmoudashraf.data.sources.remote.WordsRemoteDataSource
import com.mahmoudashraf.domain.repositories.WordsRepository

class WordsRepositoryImpl(private val wordsRemoteDataSource: WordsRemoteDataSource) : WordsRepository {
    override fun getWords(): Any {
        return wordsRemoteDataSource.getWords()
    }
}
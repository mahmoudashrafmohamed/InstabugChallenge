package com.mahmoudashraf.domain.repositories

interface WordsRepository {
    fun getWords() : List<String>
}
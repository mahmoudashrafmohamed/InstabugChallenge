package com.mahmoudashraf.data.sources.local

interface WordsLocalDataSource {
    fun getWords(): List<String>
    fun saveWords(words: List<String>)
}
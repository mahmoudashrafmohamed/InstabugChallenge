package com.mahmoudashraf.local

interface WordsLocalDataSource {
    fun getWords(): List<String>
    fun saveWords(words: List<String>)
}
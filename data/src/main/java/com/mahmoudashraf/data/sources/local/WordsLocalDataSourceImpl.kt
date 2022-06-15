package com.mahmoudashraf.data.sources.local

class WordsLocalDataSourceImpl(private val dbHelper: DatabaseHelper) : WordsLocalDataSource {
    override fun getWords(): List<String> {
        return dbHelper.getWords()
    }

    override fun saveWords(words: List<String>) {
        return dbHelper.saveWords(words)
    }
}
package com.mahmoudashraf.local

import com.mahmoudashraf.core.base.appContext

class WordsLocalDataSourceImpl(private val dbHelper: DatabaseHelper = DatabaseHelper(appContext)) :
    WordsLocalDataSource {
    override fun getWords(): List<String> {
        return dbHelper.getWords()
    }

    override fun saveWords(words: List<String>) {
        return dbHelper.saveWords(words)
    }
}
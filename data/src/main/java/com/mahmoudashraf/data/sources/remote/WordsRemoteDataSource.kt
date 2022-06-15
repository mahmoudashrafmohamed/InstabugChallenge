package com.mahmoudashraf.data.sources.remote

interface WordsRemoteDataSource {
    fun getWords(): List<String>
}
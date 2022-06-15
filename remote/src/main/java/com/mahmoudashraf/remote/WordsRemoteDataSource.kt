package com.mahmoudashraf.remote

interface WordsRemoteDataSource {
    fun getWords(): List<String>
}
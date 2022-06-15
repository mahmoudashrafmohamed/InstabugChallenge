package com.mahmoudashraf.data.repositories

import com.mahmoudashraf.local.WordsLocalDataSource
import com.mahmoudashraf.remote.WordsRemoteDataSource
import org.junit.Assert
import org.junit.Test


class WordsRepositoryImplTest {

    @Test
    fun `getWords() when invoke then return listOf words()`() {
        // arrange
        val words = listOf("android", "test")
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                return words
            }
        }
        val localDataSource = object : WordsLocalDataSource {
            override fun getWords(): List<String> {
                return words
            }

            override fun saveWords(words: List<String>) {}
        }
        val wordsRepositoryImpl =
            WordsRepositoryImpl(remoteDataSource, localDataSource, isInternetAvailable = true)
        // act
        val result = wordsRepositoryImpl.getWords()
        // assert
        Assert.assertEquals(words, result)
    }

    @Test
    fun `getWords() when invoke then throw Exception`() {
        // arrange
        val words = listOf("android", "test")
        val exception = Exception("something wrong happened")
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                throw exception
            }
        }
        val localDataSource = object : WordsLocalDataSource {
            override fun getWords(): List<String> {
                return words
            }

            override fun saveWords(words: List<String>) {}
        }
        val wordsRepositoryImpl =
            WordsRepositoryImpl(remoteDataSource, localDataSource, isInternetAvailable = true)
        // assert
        Assert.assertThrows(Exception::class.java) { wordsRepositoryImpl.getWords() }
    }

    @Test
    fun `getWords() when invoke and isInternetAvailable then invoke remoteDataSource()`() {
        // arrange
        val words = listOf("android", "test")
        var isRemoteInvoked = false
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                isRemoteInvoked = true
                return words
            }
        }
        val localDataSource = object : WordsLocalDataSource {
            override fun getWords(): List<String> {
                return words
            }
            override fun saveWords(words: List<String>) {}
        }
        val wordsRepositoryImpl =
            WordsRepositoryImpl(remoteDataSource, localDataSource, isInternetAvailable = true)
        // act
        wordsRepositoryImpl.getWords()
        // assert
        Assert.assertEquals(true, isRemoteInvoked)
    }

    @Test
    fun `getWords() when invoke and isInternetAvailable then invoke saveWords()`() {
        // arrange
        val words = listOf("android", "test")
        var isSaveInvoked = false
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                return words
            }
        }
        val localDataSource = object : WordsLocalDataSource {
            override fun getWords(): List<String> {
                return words
            }
            override fun saveWords(words: List<String>) {
                isSaveInvoked = true
            }
        }
        val wordsRepositoryImpl =
            WordsRepositoryImpl(remoteDataSource, localDataSource, isInternetAvailable = true)
        // act
        wordsRepositoryImpl.getWords()
        // assert
        Assert.assertEquals(true, isSaveInvoked)
    }

    @Test
    fun `getWords() when invoke and isInternetNotAvailable then invoke localDataSource()`() {
        // arrange
        val words = listOf("android", "test")
        var isLocalInvoked = false
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                return words
            }
        }
        val localDataSource = object : WordsLocalDataSource {
            override fun getWords(): List<String> {
                isLocalInvoked = true
                return words
            }
            override fun saveWords(words: List<String>) {}
        }
        val wordsRepositoryImpl =
            WordsRepositoryImpl(remoteDataSource, localDataSource, isInternetAvailable = false)
        // act
        wordsRepositoryImpl.getWords()
        // assert
        Assert.assertEquals(true, isLocalInvoked)
    }


}
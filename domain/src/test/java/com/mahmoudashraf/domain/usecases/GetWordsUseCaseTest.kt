package com.mahmoudashraf.domain.usecases

import com.mahmoudashraf.domain.repositories.WordsRepository
import org.junit.Assert
import org.junit.Test


class GetWordsUseCaseTest {

    @Test
    fun `GetWordsUseCase when invoke then  emit words()`() {
        // arrange
        val words = listOf("android", "test")
        val wordsRepositoryImpl = object : WordsRepository {
            override fun getWords(): List<String> {
                return words
            }

        }
        val getWordsUseCase = GetWordsUseCase(wordsRepositoryImpl)
        // act
        val result = getWordsUseCase.invoke()
        // assert
        Assert.assertEquals(words, result)
    }

    @Test
    fun `GetWordsUseCase when invoke then throw exception`() {
        // arrange
        val wordsRepositoryImpl = object : WordsRepository {
            override fun getWords(): List<String> {
                throw Exception()
            }

        }
        val getWordsUseCase = GetWordsUseCase(wordsRepositoryImpl)
        // assert
        Assert.assertThrows(Exception::class.java){getWordsUseCase.invoke()}
    }

}
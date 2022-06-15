package com.mahmoudashraf.remote

import org.junit.Assert
import org.junit.Test
import java.io.IOException


class WordsAPITest {

    @Test
    fun `getWords() when invoke then return listOf words()`() {
        // arrange
        val words = listOf( "android", "test")
        val remoteDataSource = object : WordsRemoteDataSource {
            val content = "android test "
            override fun getWords(): List<String> {
                return content
                    .extractCharactersAndSpacesOnly()
                    .trim()
                    .split("\\s+".toRegex())
            }
        }
        // act
        val result = remoteDataSource.getWords()
        // assert
        Assert.assertEquals(words, result)

    }

    @Test
    fun `getWords() when invoke then throw exception`() {
        // arrange
        val exception = IOException("400")
        val remoteDataSource = object : WordsRemoteDataSource {
            override fun getWords(): List<String> {
                throw exception
            }
        }
        // assert
        Assert.assertThrows(exception::class.java) {remoteDataSource.getWords()}

    }

    @Test
    fun `extractCharactersAndSpacesOnly() when invoke then return characters and spaces only`() {
        // arrange
        val content = "#^^ android test"
        //act
        val result = content.extractCharactersAndSpacesOnly()
        // assert
        Assert.assertEquals("    android test",result)

    }

}
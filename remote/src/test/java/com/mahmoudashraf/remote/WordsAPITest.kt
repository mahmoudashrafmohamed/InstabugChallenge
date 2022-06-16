package com.mahmoudashraf.remote

import org.junit.Assert
import org.junit.Test


class WordsAPITest {

    @Test
    fun `extractCharactersAndSpacesOnly() when invoke then return characters and spaces only`() {
        // arrange
        val content = "#^^ android test"
        //act
        val result = content.extractCharactersAndSpacesOnly()
        // assert
        Assert.assertEquals("    android test",result)
    }

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

}
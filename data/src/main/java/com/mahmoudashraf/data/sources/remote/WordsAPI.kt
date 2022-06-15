package com.mahmoudashraf.data.sources.remote

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection

const val BASE_URL = "https://instabug.com"

class WordsAPI : WordsRemoteDataSource {

    private val url = URL(BASE_URL)

    override fun getWords(): List<String> {
        val words = StringBuilder()
        try {
            val httpURLConnection = url.openConnection() as HttpURLConnection
            when (httpURLConnection.responseCode) {
                HttpsURLConnection.HTTP_OK -> {
                    val inputStreamReader =
                        InputStreamReader(httpURLConnection.inputStream, Charset.forName("UTF-8"))
                    val br = BufferedReader(inputStreamReader)
                    var line: String?
                    while (br.readLine().also { line = it } != null) {
                        words.append(line).append("\n")
                    }
                }
                else -> {
                    throw IOException("${httpURLConnection.responseCode}")
                }
            }

        } catch (e: Exception) {
            throw Exception("something wrong happened!")
        }
        return words
            .toString()
            .extractCharactersAndSpacesOnly()
            .trim()
            .split("\\s+".toRegex())
    }
}


fun String.extractCharactersAndSpacesOnly(): String {
    val regex = "[^A-Za-z ]".toRegex()
    return regex.replace(this, " ")
}
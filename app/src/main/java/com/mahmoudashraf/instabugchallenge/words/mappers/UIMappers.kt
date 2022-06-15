package com.mahmoudashraf.instabugchallenge.words.mappers

import com.mahmoudashraf.instabugchallenge.words.model.WordUIModel

fun List<String>.mapToWordsListUIModel() : List<WordUIModel> {
    val wordWithCountHashMap = HashMap<String, Int>()
    this.forEach {
        var count = wordWithCountHashMap[it] ?: 0
        wordWithCountHashMap[it] = ++count
    }
    return wordWithCountHashMap.map {
        WordUIModel(it.key,it.value)
    }
}
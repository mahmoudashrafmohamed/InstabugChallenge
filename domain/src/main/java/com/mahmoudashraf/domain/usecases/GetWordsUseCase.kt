package com.mahmoudashraf.domain.usecases

import com.mahmoudashraf.domain.repositories.WordsRepository

class GetWordsUseCase(private val wordsRepository: WordsRepository) {
     operator fun invoke() = wordsRepository.getWords()
}
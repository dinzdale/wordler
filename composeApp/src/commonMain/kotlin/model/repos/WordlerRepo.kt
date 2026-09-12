package model.repos

import Network.WordlerAPI
import androidx.compose.foundation.layout.Box
import model.Definition
import model.DictionaryItem
import model.ui.game_pieces.WordDictionary


object WordlerRepo {
    private const val NOTRIES = 3
    private const val NOWORDS = 3
    suspend fun getWordsAndDefinitions(noWords: Int = NOWORDS): Result<Map<String, List<DictionaryItem>>> {
        var tries = 0
        var wordsAndDefs = mutableMapOf<String, List<DictionaryItem>>()
        var lastError: Throwable? = null

        while (wordsAndDefs.size < noWords && tries < NOTRIES) {
            val result = WordlerAPI.getWords(noWords)
            result.onSuccess { wordList ->
                wordList.map { it.word }.forEach { nxtWord ->
                    WordlerAPI.getDictionaryDefinition(nxtWord).onSuccess { dictionaryItemList ->
                        wordsAndDefs[nxtWord] = dictionaryItemList
                    }
                }
            }
            result.onFailure {
                lastError = it
            }
            ++tries
        }
        
        return if (wordsAndDefs.isEmpty()) {
            Result.failure(lastError ?: Exception("Failed to load words and definitions"))
        } else {
            Result.success(wordsAndDefs)
        }
    }

    suspend fun getDictionary(word: String) = WordlerAPI.getDictionaryDefinition(word)

}

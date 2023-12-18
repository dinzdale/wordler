package model.repos

import Network.WordlerAPI
import androidx.compose.foundation.layout.Box
import model.Definition
import model.DictionaryItem


object WordlerRepo {
    private const val NOTRIES = 3
    private const val NOWORDS = 3
    suspend fun getWordsAndDefinitions(noWords: Int = NOWORDS): Map<String, List<DictionaryItem>> {
        var tries = 0
        var wordsAndDefs = mutableMapOf<String, List<DictionaryItem>>()
        while (wordsAndDefs.size < noWords && tries < NOTRIES) {
            WordlerAPI.getWords(noWords).apply {
                onSuccess { wordList ->
                    wordList.forEach { nxtWord ->
                        WordlerAPI.getDictionaryDefinition(nxtWord).apply {
                            onSuccess { dictionaryItemList ->
                                wordsAndDefs[nxtWord] = dictionaryItemList
                            }
                        }
                    }
                }
                onFailure {

                }
            }
            ++tries
        }
        return wordsAndDefs
    }
}
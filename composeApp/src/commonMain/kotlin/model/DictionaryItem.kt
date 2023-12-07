package model


import kotlinx.serialization.Serializable

@Serializable
data class DictionaryItem(
    val license: License,
    val meanings: List<Meaning>,
    val phonetic: String?=null,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
)
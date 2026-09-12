package Network

import kotlinx.serialization.Serializable
import model.Definition
import model.DictionaryItem
import model.License
import model.Meaning
import model.Phonetic

@Serializable
data class FreeDictionaryResponse(
    val word: String,
    val entries: List<FreeDictionaryEntry>,
    val source: FreeDictionarySource? = null
)

@Serializable
data class FreeDictionaryEntry(
    val partOfSpeech: String,
    val senses: List<FreeDictionarySense>
)

@Serializable
data class FreeDictionarySense(
    val definition: String,
    val examples: List<String>? = null,
    val synonyms: List<String>? = null,
    val antonyms: List<String>? = null
)

@Serializable
data class FreeDictionarySource(
    val url: String? = null,
    val license: FreeDictionaryLicense? = null
)

@Serializable
data class FreeDictionaryLicense(
    val name: String? = null,
    val url: String? = null
)

fun FreeDictionaryResponse.toDictionaryItems(): List<DictionaryItem> {
    val meanings = entries.map { entry ->
        Meaning(
            antonyms = emptyList(),
            synonyms = emptyList(),
            partOfSpeech = entry.partOfSpeech,
            definitions = entry.senses.map { sense ->
                Definition(
                    definition = sense.definition,
                    example = sense.examples?.firstOrNull(),
                    synonyms = sense.synonyms ?: emptyList(),
                    antonyms = sense.antonyms ?: emptyList()
                )
            }
        )
    }
    
    return listOf(
        DictionaryItem(
            word = word,
            meanings = meanings,
            license = License(
                name = source?.license?.name ?: "Unknown",
                url = source?.url ?: source?.license?.url ?: ""
            ),
            phonetics = emptyList(),
            sourceUrls = source?.url?.let { listOf(it) } ?: emptyList()
        )
    )
}

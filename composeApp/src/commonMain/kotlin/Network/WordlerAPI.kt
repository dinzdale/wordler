package Network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.DictionaryItem

object WordlerAPI {
    private val client by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
                connectTimeoutMillis = 5000
                socketTimeoutMillis = 5000
            }
        }
    }

    suspend fun getWords(noWords: Int = 3, length: Int = 5): Result<RandomWordsContract> {
        val providers = listOf(
            "kushcreates" to ::getWordsRandomlURL,
            "herokuapp" to ::getWordsHeroKuapp,
            "datamuse" to ::getWordsDatamuseURL
        )

        for ((name, provider) in providers) {
            try {
                val url = provider(noWords, length)
                val response = client.get(url)
                if (response.status.value in 200..299) {
                    val words = when (name) {
                        "kushcreates" -> response.body<RandomWordsContract>()
                        "herokuapp" -> response.body<List<String>>().map {
                            RandomWordsContractItem("unknown", "en", length, it)
                        }
                        "datamuse" -> response.body<List<DatamuseItem>>().map {
                            RandomWordsContractItem("unknown", "en", length, it.word)
                        }
                        else -> emptyList()
                    }
                    if (words.isNotEmpty()) {
                        return Result.success(words)
                    }
                }
            } catch (_: Exception) {
                // Try next provider
            }
        }
        return Result.failure(Exception("All random word providers failed"))
    }

    suspend fun getDictionaryDefinition(word: String): Result<List<DictionaryItem>> {
        // Try FreeDictionaryAPI.com first as it's currently more reliable
        try {
            val response = client.get("https://freedictionaryapi.com/api/v1/entries/en/$word")
            if (response.status.value == 200) {
                val freeDictResponse = response.body<FreeDictionaryResponse>()
                return Result.success(freeDictResponse.toDictionaryItems())
            }
        } catch (_: Exception) {
            // Fallback
        }

        // Try DictionaryAPI.dev as fallback
        try {
            val response = client.get("https://api.dictionaryapi.dev/api/v2/entries/en/$word")
            if (response.status.value == 200) {
                return Result.success(response.body())
            }
        } catch (_: Exception) {
            // Fail
        }

        return Result.failure(Exception("Definition not found for $word"))
    }

    private fun getWordsHeroKuapp(noWords: Int, length: Int) =
        "https://random-word-api.herokuapp.com/word?number=${noWords}&length=${length}"

    private fun getWordsVercelURL(noWords: Int, length: Int) =
        "https://random-word-api.vercel.app/api?words=${noWords}&length=${length}"

    private fun getWordsRandomlURL(noWords: Int, length: Int) =
        "https://random-words-api.kushcreates.com/api?words=${noWords}&length=${length}"

    private fun getWordsDatamuseURL(noWords: Int, length: Int): String {
        val pattern = "?".repeat(length)
        return "https://api.datamuse.com/words?sp=$pattern&max=$noWords"
    }
}

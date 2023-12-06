package Network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import models.DictionaryItem

object WordlerAPI {
    private val client by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    suspend fun getWords(noWords: Int = 3, length: Int = 5): Result<List<String>> {
        return try {
            Result.success(client.get(
                "https://random-word-api.herokuapp.com/word?number=${noWords}&length=${length}")
                .body())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    suspend fun getDictionaryDefinition(word: String): Result<List<DictionaryItem>> {
        return try {
            Result.success(
                client.get("https://api.dictionaryapi.dev/api/v2/entries/en/$word").body())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

}

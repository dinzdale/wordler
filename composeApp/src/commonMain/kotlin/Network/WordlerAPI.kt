package Network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import model.DictionaryItem


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
                getWordsVercelURL(noWords,length))
                .body())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

//    suspend fun getWords(noWords: Int = 3, length: Int = 5): Result<List<String>> {
//        return Result.success(listOf("await", "smell", "silly"))
//    }


    suspend fun getDictionaryDefinition(word: String): Result<List<DictionaryItem>> {
        return try {
            Result.success(
                client.get("https://api.dictionaryapi.dev/api/v2/entries/en/$word").body()
            )
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private fun getWordsHeroKuapp(noWords: Int, length: Int) =
        "https://random-word-api.herokuapp.com/word?number=${noWords}&length=${length}"

    private fun getWordsVercelURL(noWords: Int, length: Int) =
        "https://random-word-api.vercel.app/api?words=${noWords}&length=${length}"
}

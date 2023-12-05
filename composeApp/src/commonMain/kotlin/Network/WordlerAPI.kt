package Network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

object WordlerAPI {
    private val client by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    suspend fun getWords(noWords: Int = 3, length: Int = 5): List<String> {
        return client.get("https://random-word-api.herokuapp.com/word?number=${noWords}&length=${length}")
            .body()
    }
}

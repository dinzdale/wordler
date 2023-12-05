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

    suspend fun getWords(): List<String> {
        return client.get("https://random-word-api.herokuapp.com/word?number=10&length=5").body()
    }
}

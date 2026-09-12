package Network

import kotlinx.serialization.Serializable

@Serializable
data class DatamuseItem(
    val word: String,
    val score: Int? = null
)

package Network

import kotlinx.serialization.Serializable

typealias RandomWordsContract = List<RandomWordsContractItem>

@Serializable
data class RandomWordsContractItem(
    val category: String,
    val language: String,
    val length: Int,
    val word: String
)

package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Phonetic(
    val audio: String? = null,
    val license: License? = null,
    val sourceUrl: String? = null,
    val text: String? = null
)
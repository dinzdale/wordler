package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Phonetic(
    val audio: String,
    val license: License?=null,
    val sourceUrl: String?=null,
    val text: String
)
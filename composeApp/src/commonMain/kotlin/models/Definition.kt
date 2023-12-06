package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Definition(
    val antonyms: List<String>,
    val definition: String,
    val example: String?=null,
    val synonyms: List<String>
)
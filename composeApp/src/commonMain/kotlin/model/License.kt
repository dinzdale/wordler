package model


import kotlinx.serialization.Serializable

@Serializable
data class License(
    val name: String,
    val url: String
)
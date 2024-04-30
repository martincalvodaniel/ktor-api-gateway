package com.dmartinc.model

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: Name,
    val age: String,
    val gender: String,
    val species: String,
    val homePlanet: String? = null,
    val occupation: String,
    val sayings: List<String>,
    val images: Images
) {

    @Serializable
    data class Name(
        val first: String,
        val middle: String,
        val last: String
    )

    @Serializable
    data class Images(
        val headShot: String? = null,
        val main: String
    )
}
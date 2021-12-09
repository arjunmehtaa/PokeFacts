package com.example.domain.model

import java.io.Serializable

data class Pokemon(
    val base_experience: Int,
    val height: Int,
    var id: Int,
    var name: String,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int,
    var dominant_color: Int?,
    var genera: String,
    var description: String,
    var capture_rate: Int,
    var url: String? = ""
) : Serializable
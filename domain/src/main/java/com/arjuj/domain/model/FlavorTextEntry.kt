package com.arjuj.domain.model

import java.io.Serializable

data class FlavorTextEntry(
    val flavor_text: String,
    val language : Language
) : Serializable
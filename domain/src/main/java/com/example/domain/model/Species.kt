package com.example.domain.model

import java.io.Serializable

data class Species(
    var genera: List<Genera>,
    var flavor_text_entries: List<FlavorTextEntry>,
    var capture_rate : Int
) : Serializable
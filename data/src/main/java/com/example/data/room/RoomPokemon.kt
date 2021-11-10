package com.example.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_list")
data class RoomPokemon(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val base_experience: Int,
    val height: Int,
    var name: String,
    val sprites: String,
    val stats: String,
    val types: String,
    val weight: Int,
    var dominant_color : Int?,
    val genera : String,
    val description : String,
    var capture_rate : Int
)
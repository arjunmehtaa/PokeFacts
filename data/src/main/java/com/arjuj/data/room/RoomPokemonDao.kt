package com.arjuj.data.room

import androidx.room.*

@Dao
interface RoomPokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRoomPokemonItem(roomPokemon: RoomPokemon)

    @Query("SELECT * FROM favorites_list ORDER BY id ASC")
    fun readAllRoomItems() : List<RoomPokemon>

    @Query("SELECT EXISTS(SELECT * FROM favorites_list WHERE id = :id)")
    fun isPokemonFavorite(id : Int) : Boolean

    @Delete
    suspend fun deletePokemon(pokemon: RoomPokemon)
}
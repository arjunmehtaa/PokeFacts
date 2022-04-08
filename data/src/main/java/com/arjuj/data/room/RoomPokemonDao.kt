package com.arjuj.data.room

import androidx.room.*

@Dao
interface RoomPokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRoomPokemonItem(roomPokemon: RoomPokemon)

    @Query("SELECT * FROM pokemon_list WHERE is_favorite = 1 ORDER BY id ASC")
    fun readFavoriteItems(): List<RoomPokemon>

    @Query("SELECT * FROM pokemon_list ORDER BY id ASC")
    fun readAllItems(): List<RoomPokemon>

    @Query("SELECT * FROM pokemon_list WHERE id = :id")
    fun readSingleItem(id: Int): RoomPokemon

    @Update
    suspend fun addFavoritePokemon(pokemon: RoomPokemon)

    @Update
    suspend fun removeFavoritePokemon(pokemon: RoomPokemon)

    @Query("SELECT EXISTS(SELECT * FROM pokemon_list WHERE id = :id AND is_favorite = 1)")
    fun isPokemonFavorite(id: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM pokemon_list WHERE id = :id)")
    fun isPokemonSaved(id: Int): Boolean
}
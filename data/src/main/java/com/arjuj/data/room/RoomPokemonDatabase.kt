package com.arjuj.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RoomPokemon::class], version = 1, exportSchema = false)
abstract class RoomPokemonDatabase : RoomDatabase() {

    abstract fun roomPokemonDao() : RoomPokemonDao

    companion object{
        @Volatile
        private var INSTANCE : RoomPokemonDatabase? = null

        fun getDatabase(context: Context) : RoomPokemonDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomPokemonDatabase::class.java,
                    "room_database"
                ).allowMainThreadQueries().createFromAsset("room_database.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
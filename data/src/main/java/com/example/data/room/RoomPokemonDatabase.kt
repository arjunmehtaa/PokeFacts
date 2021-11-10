package com.example.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

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
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
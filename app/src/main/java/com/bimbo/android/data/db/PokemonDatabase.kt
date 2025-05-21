package com.bimbo.android.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bimbo.android.common.Constants.PREFERENCE_POKEMON_DB
import com.bimbo.android.data.db.dao.PokemonDao
import com.bimbo.android.data.db.model.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile private var INSTANCE: PokemonDatabase? = null

        fun getInstance(context: Context): PokemonDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    PREFERENCE_POKEMON_DB
                ).build().also { INSTANCE = it }
            }
    }
}

package com.bimbo.android.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bimbo.android.data.model.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemons(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_table WHERE name = :name LIMIT 1")
    suspend fun getPokemonByName(name: String): PokemonEntity?
}
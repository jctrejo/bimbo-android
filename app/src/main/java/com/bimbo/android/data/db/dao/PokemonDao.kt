package com.bimbo.android.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bimbo.android.data.db.model.PokemonEntity

/**
 * Interfaz DAO (Data Access Object) para la entidad Pokémon.
 *
 * Define las operaciones de base de datos para obtener y almacenar información de Pokémon
 * en la tabla `pokemon_table`.
 */
@Dao
interface PokemonDao {

    /**
     * Obtiene la lista completa de Pokémon almacenados en la base de datos.
     *
     * @return Lista de entidades [PokemonEntity].
     */
    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemons(): List<PokemonEntity>

    /**
     * Inserta una lista de Pokémon en la base de datos.
     * En caso de conflicto (por ejemplo, Pokémon con el mismo ID), reemplaza la entrada existente.
     *
     * @param pokemons Lista de entidades [PokemonEntity] a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    /**
     * Obtiene un Pokémon específico por su nombre.
     *
     * @param name Nombre del Pokémon a buscar.
     * @return La entidad [PokemonEntity] correspondiente si existe, o null si no se encuentra.
     */
    @Query("SELECT * FROM pokemon_table WHERE name = :name LIMIT 1")
    suspend fun getPokemonByName(name: String): PokemonEntity?
}
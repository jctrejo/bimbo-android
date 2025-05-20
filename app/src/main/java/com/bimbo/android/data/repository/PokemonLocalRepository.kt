package com.bimbo.android.data.repository

import com.bimbo.android.data.api.PokeApiService
import com.bimbo.android.data.db.dao.PokemonDao
import com.bimbo.android.data.model.PokemonEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonLocalRepository @Inject constructor(
    private val dao: PokemonDao,
    private val apiService: PokeApiService
) {

    suspend fun getPokemons(): List<PokemonEntity> {
        val localList = dao.getAllPokemons()
        return if (localList.isNotEmpty()) {
            localList
        } else {
            val response = apiService.getPokemonList(limit = 100)
            val pokemonEntities = response.results.mapIndexed { index, pokemon ->
                PokemonEntity(
                    id = index + 1,
                    name = pokemon.name,
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${index + 1}.png"
                )
            }
            dao.insertPokemons(pokemonEntities)
            pokemonEntities
        }
    }

    suspend fun getPokemonByName(name: String): PokemonEntity? {
        return dao.getPokemonByName(name)
    }
}
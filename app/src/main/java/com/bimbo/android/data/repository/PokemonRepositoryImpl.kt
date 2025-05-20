package com.bimbo.android.data.repository

import com.bimbo.android.data.api.PokeApiService
import com.bimbo.android.data.db.dao.PokemonDao
import com.bimbo.android.data.model.PokemonEntity
import com.bimbo.android.domain.model.Pokemon
import com.bimbo.android.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val apiService: PokeApiService
) {
   /* suspend fun refreshPokemonList() {
        val response = apiService.getPokemonList(limit = 100)
        val pokemonEntities = response.results.mapIndexed { index, pokemon ->
            PokemonEntity(
                id = index + 1,
                name = pokemon.name,
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${index + 1}.png"
            )
        }
        pokemonDao.insertPokemons(pokemonEntities)  // Inserción en background
    }*/
}

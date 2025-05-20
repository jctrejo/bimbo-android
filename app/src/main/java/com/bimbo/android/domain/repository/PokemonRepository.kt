package com.bimbo.android.domain.repository

import com.bimbo.android.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonList(): List<Pokemon>
    suspend fun getPokemonDetail(id: Int): Pokemon
}
package com.bimbo.android.data.api

import com.bimbo.android.common.Constants.VALUE_100
import com.bimbo.android.domain.model.PokemonDetailResponse
import com.bimbo.android.domain.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int = VALUE_100): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetailResponse
}

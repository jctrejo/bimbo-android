package com.bimbo.android.data.repository

import com.bimbo.android.common.AppResource
import com.bimbo.android.common.Constants.EXTENSION_PNG
import com.bimbo.android.common.Constants.URL_IMAGE
import com.bimbo.android.common.Constants.VALUE_1
import com.bimbo.android.common.Constants.VALUE_100
import com.bimbo.android.data.api.PokeApiService
import com.bimbo.android.data.db.dao.PokemonDao
import com.bimbo.android.data.db.model.PokemonEntity
import com.bimbo.android.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonLocalRepository @Inject constructor(
    private val dao: PokemonDao,
    private val apiService: PokeApiService
) {

    suspend fun getPokemons(): Flow<AppResource<List<PokemonEntity>>> =
        flow {
            emit(AppResource.Loading)
            try {
                val localList = dao.getAllPokemons()
                val result = if (localList.isNotEmpty()) {
                    localList
                } else {
                    val response = apiService.getPokemonList(limit = VALUE_100)
                    val pokemonEntities = response.results.mapIndexed { index, pokemon ->
                        PokemonEntity(
                            id = index + VALUE_1,
                            name = pokemon.name,
                            imageUrl = "$URL_IMAGE${index + VALUE_1}$EXTENSION_PNG"
                        )
                    }
                    dao.insertPokemons(pokemonEntities)
                    pokemonEntities
                }
                emit(AppResource.Success(result))
            } catch (e: HttpException) {
                emit(AppResource.Error(e.code().toString()))
            }
        }.catch { e ->
            if (e is IOException && e.message?.contains("timeout") == true) {
                emit(AppResource.Error("Error de tiempo de espera. Inténtalo de nuevo."))
            } else {
                emit(AppResource.Error(e.message ?: "Ocurrió un error. Inténtalo de nuevo."))
            }
        }


    suspend fun getPokemonDetail(nameOrId: String): Flow<AppResource<PokemonDetail>> =
        flow {
            emit(AppResource.Loading)
            try {
                val response = apiService.getPokemonDetail(nameOrId)
                val result = PokemonDetail(
                    id = response.id,
                    name = response.name,
                    imageUrl = response.sprites.frontDefault,
                    height = response.height,
                    weight = response.weight,
                    types = response.types.sortedBy { it.slot }.map { it.type.name },
                    description = response.description
                )

                emit(AppResource.Success(result))
            } catch (e: HttpException) {
                emit(AppResource.Error(e.code().toString()))
            }
        }.catch { e ->
            if (e is IOException && e.message?.contains("timeout") == true) {
                emit(AppResource.Error("Error de tiempo de espera. Inténtalo de nuevo."))
            } else {
                emit(AppResource.Error(e.message ?: "Ocurrió un error. Inténtalo de nuevo."))
            }
        }
}

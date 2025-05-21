package com.bimbo.android.domain.usecase

import com.bimbo.android.common.AppResource
import com.bimbo.android.data.db.model.PokemonEntity
import com.bimbo.android.data.repository.PokemonLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonListUseCase @Inject constructor(
    private val repository: PokemonLocalRepository
) {
    suspend operator fun invoke(): Flow<AppResource<List<PokemonEntity>>> =
        repository.getPokemons()
}

package com.bimbo.android.domain.usecase

import com.bimbo.android.common.AppResource
import com.bimbo.android.data.repository.PokemonLocalRepository
import com.bimbo.android.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonDetailUseCase @Inject constructor(
    private val repository: PokemonLocalRepository
) {
    suspend operator fun invoke(nameOrId: String): Flow<AppResource<PokemonDetail>> =
        repository.getPokemonDetail(nameOrId)
}

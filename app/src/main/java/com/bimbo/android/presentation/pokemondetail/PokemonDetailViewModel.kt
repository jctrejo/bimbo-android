package com.bimbo.android.presentation.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimbo.android.common.AppResource
import com.bimbo.android.common.AppUiState
import com.bimbo.android.domain.model.PokemonDetail
import com.bimbo.android.domain.usecase.PokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel encargado de manejar la lógica relacionada con el detalle de un Pokémon.
 *
 * Utiliza Hilt para la inyección de dependencias mediante la anotación [HiltViewModel].
 * Expone un [LiveData] con el detalle del Pokémon para que la UI pueda observarlo.
 *
 * @property repository Repositorio local para acceder a los datos del Pokémon.
 */
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonDetailUseCase: PokemonDetailUseCase,
) : ViewModel() {

    /**
     * LiveData que contiene el detalle del Pokémon.
     */

    private val _pokemonDetail = MutableLiveData<AppUiState<PokemonDetail>?>()
    val pokemonDetail: LiveData<AppUiState<PokemonDetail>?> get() = _pokemonDetail

    /**
     * Carga el detalle de un Pokémon dado su nombre o ID.
     * Realiza la llamada al repositorio de forma asíncrona y actualiza el LiveData.
     *
     * @param nameOrId Nombre o ID del Pokémon a cargar.
     */
    fun loadPokemonDetail(nameOrId: String) {
        viewModelScope.launch {
            pokemonDetailUseCase.invoke(nameOrId).collect { result ->
                when (result) {
                    is AppResource.Loading -> {
                        _pokemonDetail.postValue(AppUiState.Loading())
                    }

                    is AppResource.Success -> {
                        _pokemonDetail.postValue(AppUiState.Success(result.item))
                    }

                    is AppResource.Error -> {
                        _pokemonDetail.postValue(AppUiState.Error(result.throwable))
                    }
                }
            }
        }
    }
}

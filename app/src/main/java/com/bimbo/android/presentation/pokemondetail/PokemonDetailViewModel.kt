package com.bimbo.android.presentation.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimbo.android.data.repository.PokemonLocalRepository
import com.bimbo.android.domain.model.PokemonDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonLocalRepository
) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<PokemonDetail>()
    val pokemonDetail: LiveData<PokemonDetail> = _pokemonDetail

    fun loadPokemonDetail(nameOrId: String) {
        viewModelScope.launch {
            try {
                val detail = repository.getPokemonDetail(nameOrId)
                _pokemonDetail.postValue(detail)
            } catch (e: Exception) {
                // Manejar error (mostrar mensaje, etc)
            }
        }
    }
}
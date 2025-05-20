package com.bimbo.android.presentation.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimbo.android.data.model.PokemonEntity
import com.bimbo.android.data.repository.PokemonLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonLocalRepository
) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<PokemonEntity?>()
    val pokemonDetail: LiveData<PokemonEntity?> = _pokemonDetail

    fun loadPokemonDetail(name: String) {
        viewModelScope.launch {
            val pokemon = repository.getPokemonByName(name)
            _pokemonDetail.postValue(pokemon)
        }
    }
}
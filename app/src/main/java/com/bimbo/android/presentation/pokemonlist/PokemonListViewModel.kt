package com.bimbo.android.presentation.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimbo.android.data.model.PokemonEntity
import com.bimbo.android.data.repository.PokemonLocalRepository
import com.bimbo.android.di.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonLocalRepository
) : ViewModel() {

    val usernameFlow: StateFlow<String?> = PreferencesManager.usernameFlow

    private val _pokemonList = MutableLiveData<List<PokemonEntity>>()
    val pokemonList: LiveData<List<PokemonEntity>> = _pokemonList

    fun loadPokemons() {
        viewModelScope.launch {
            val list = repository.getPokemons()
            _pokemonList.postValue(list)
        }
    }
}

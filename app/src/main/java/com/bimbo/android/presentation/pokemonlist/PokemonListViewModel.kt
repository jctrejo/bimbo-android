package com.bimbo.android.presentation.pokemonlist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimbo.android.common.AppResource
import com.bimbo.android.common.AppUiState
import com.bimbo.android.common.Constants.PREFS_NAME
import com.bimbo.android.data.db.model.PokemonEntity
import com.bimbo.android.di.PreferencesManager
import com.bimbo.android.domain.usecase.PokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que maneja la lógica relacionada con la lista de Pokémon.
 *
 * Utiliza Hilt para la inyección de dependencias mediante la anotación [HiltViewModel].
 * Expone un flujo con el nombre de usuario y una lista observable de entidades Pokémon.
 *
 * @property repository Repositorio local para acceder a los datos de Pokémon.
 */
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonListUseCase
) : ViewModel() {

    /**
     * Flujo que emite el nombre de usuario almacenado en las preferencias.
     */
    val usernameFlow: StateFlow<String?> = PreferencesManager.usernameFlow

    /**
     * LiveData que contiene la lista de Pokémon obtenida del repositorio.
     */

    private val _pokemonList = MutableLiveData<AppUiState<List<PokemonEntity>>?>()
    val pokemonList: LiveData<AppUiState<List<PokemonEntity>>?> get() = _pokemonList

    /**
     * Carga la lista de Pokémon desde el repositorio de forma asíncrona
     * y actualiza el LiveData [_pokemonList].
     */
    fun loadPokemons() {
        viewModelScope.launch {
            repository.invoke().collect { result ->
                when (result) {
                    is AppResource.Loading -> {
                        _pokemonList.postValue(AppUiState.Loading())
                    }

                    is AppResource.Success -> {
                        _pokemonList.postValue(AppUiState.Success(result.item))
                    }

                    is AppResource.Error -> {
                        _pokemonList.postValue(AppUiState.Error(result.throwable))
                    }
                }
            }
        }
    }

    /**
     * Carga el detalle de un Pokémon dado su nombre o ID.
     * Realiza la llamada al repositorio de forma asíncrona y actualiza el LiveData.
     *
     * @param nameOrId Nombre o ID del Pokémon a cargar.
     */


    /**
     * Actualiza el estado de login en las preferencias.
     *
     * @param isLogin Estado de login a guardar (`true` si está logueado, `false` si no).
     */
    fun logOut(isLogin: Boolean) {
        PreferencesManager.isLogin = isLogin
    }

    /**
     * Limpia la sesión del usuario eliminando todas las preferencias almacenadas.
     *
     * @param context Contexto necesario para acceder a las preferencias compartidas.
     */
    fun clearUserSession(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}

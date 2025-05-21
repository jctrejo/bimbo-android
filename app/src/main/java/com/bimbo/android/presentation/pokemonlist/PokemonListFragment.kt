package com.bimbo.android.presentation.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimbo.android.presentation.init.MainActivity
import com.bimbo.android.R
import com.bimbo.android.common.AppUiState
import com.bimbo.android.common.Constants.EMPTY
import com.bimbo.android.databinding.FragmentPokemonListBinding
import com.bimbo.android.utils.extensions.navigateToMainAndClearStack
import com.bimbo.android.utils.loader.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragmento que muestra una lista de Pokémon.
 *
 * Utiliza Hilt para la inyección de dependencias ([AndroidEntryPoint]).
 * Este fragmento se encarga de mostrar la lista de Pokémon,
 * permitir la navegación al detalle de cada Pokémon y gestionar el logout del usuario.
 */
@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    /**
     * Binding para acceder a las vistas del layout fragment_pokemon_list.xml.
     * Se inicializa de forma lazy para asegurar que solo se instancia cuando es necesario.
     */
    private val binding by lazy { FragmentPokemonListBinding.inflate(layoutInflater) }

    /**
     * ViewModel asociado a este fragmento, proporcionado por Hilt.
     * Se utiliza para obtener los datos de la lista de Pokémon y gestionar la sesión del usuario.
     */
    private val viewModel: PokemonListViewModel by viewModels()

    /**
     * Adapter para el RecyclerView que muestra la lista de Pokémon.
     */
    private lateinit var adapter: PokemonAdapter

    private lateinit var loader: LoadingDialog

    /**
     * Infla el layout del fragmento.
     *
     * @param inflater LayoutInflater utilizado para inflar la vista.
     * @param container ViewGroup padre al que se adjuntará la vista.
     * @param savedInstanceState Bundle que contiene el estado previamente guardado del fragmento.
     * @return La vista del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    /**
     * Se llama después de que la vista del fragmento ha sido creada.
     * Aquí se configuran las vistas y se observa el LiveData del ViewModel.
     *
     * @param view Vista del fragmento.
     * @param savedInstanceState Bundle que contiene el estado previamente guardado del fragmento.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = LoadingDialog(binding.root.context)
        setupView()
    }

    /**
     * Configura las vistas del fragmento, incluyendo el RecyclerView,
     * la observación del flujo de nombre de usuario y el listener del botón de logout.
     */
    private fun setupView() = binding.apply {
        observer()
        setupAdapter()

        // Carga la lista de Pokémon
        viewModel.loadPokemons()

        // Observa el flujo de nombre de usuario y actualiza el TextView correspondiente
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usernameFlow.collect { username ->
                    binding.tvUserName.text = getString(R.string.user_greeting, username ?: EMPTY)
                }
            }
        }

        // Configura el listener del botón de logout
        btnLogout.setOnClickListener {
            viewModel.clearUserSession(binding.root.context)
            viewModel.logOut(false)
            navigateToMainAndClearStack(MainActivity::class.java)
        }
    }

    private fun observer() {
        // Observa la lista de Pokémon y actualiza el adapter
        viewModel.apply {
            pokemonList.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is AppUiState.Loading -> {
                        loader.show()
                    }

                    is AppUiState.Error -> {
                        loader.dismiss()
                    }

                    is AppUiState.Success -> {
                        adapter.submitList(state.data)
                        loader.dismiss()
                    }

                    else -> {}
                }
            }
        }
    }

    /**
     * Configura el RecyclerView y su adapter.
     * También observa el LiveData de la lista de Pokémon y actualiza el adapter cuando cambian los datos.
     */
    private fun setupAdapter() = binding.apply {
        adapter = PokemonAdapter { pokemon ->
            val action =
                PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(
                    pokemon.name
                )
            findNavController().navigate(action)
        }

        rvPokemonList.layoutManager = LinearLayoutManager(requireContext())
        rvPokemonList.adapter = adapter
    }
}
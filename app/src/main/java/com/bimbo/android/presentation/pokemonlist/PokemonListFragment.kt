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
import com.bimbo.android.data.common.Constants.EMPTY
import com.bimbo.android.databinding.FragmentPokemonListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private val binding by lazy { FragmentPokemonListBinding.inflate(layoutInflater) }
    private val viewModel: PokemonListViewModel by viewModels()
    private lateinit var adapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = binding.apply {
        setupAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usernameFlow.collect { username ->
                    binding.nameTextView.text = username ?: EMPTY
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = PokemonAdapter { pokemon ->
            val action =
                PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(
                    pokemon.name
                )
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.pokemonList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
        viewModel.loadPokemons()
    }
}

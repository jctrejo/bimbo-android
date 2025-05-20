package com.bimbo.android.presentation.pokemondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bimbo.android.databinding.FragmentPokemonDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonName = arguments?.getString("pokemonName") ?: return

        viewModel.pokemonDetail.observe(viewLifecycleOwner) { pokemon ->
            binding.tvName.text = pokemon?.name ?: "".replaceFirstChar { it.uppercase() }
            Glide.with(this).load(pokemon?.imageUrl).into(binding.ivPokemon)
        }

        viewModel.loadPokemonDetail(pokemonName)


        viewModel.pokemonDetail.observe(viewLifecycleOwner) { pokemon ->
            binding.tvName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            Glide.with(this).load(pokemon.imageUrl).into(binding.ivPokemon)
            binding.tvHeight.text = "Height: ${pokemon.height}"
            binding.tvWeight.text = "Weight: ${pokemon.weight}"
            binding.tvTypes.text = "Types: ${pokemon.types.joinToString(", ")}"
        }

        viewModel.loadPokemonDetail(pokemonName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
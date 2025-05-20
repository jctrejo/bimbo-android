package com.bimbo.android.presentation.pokemonlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bimbo.android.data.model.PokemonEntity
import com.bimbo.android.databinding.ItemPokemonBinding
import com.bimbo.android.domain.model.Pokemon
import com.bumptech.glide.Glide

class PokemonAdapter(
    private val onItemClick: (PokemonEntity) -> Unit
) : ListAdapter<PokemonEntity, PokemonAdapter.PokemonViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonEntity) {
            binding.tvName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            Glide.with(binding.root.context)
                .load(pokemon.imageUrl)
                .into(binding.ivPokemon)

            binding.root.setOnClickListener { onItemClick(pokemon) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PokemonEntity>() {
        override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity) = oldItem == newItem
    }
}
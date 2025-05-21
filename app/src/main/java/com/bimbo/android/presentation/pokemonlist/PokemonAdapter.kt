package com.bimbo.android.presentation.pokemonlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bimbo.android.data.db.model.PokemonEntity
import com.bimbo.android.databinding.ItemPokemonBinding
import com.bumptech.glide.Glide

/**
 * Adaptador para mostrar una lista de [PokemonEntity] en un RecyclerView.
 *
 * Utiliza [ListAdapter] junto con [DiffUtil] para manejar eficientemente los cambios en la lista.
 *
 * @property onItemClick Lambda que se ejecuta cuando se hace clic en un ítem, recibiendo el Pokémon seleccionado.
 */
class PokemonAdapter(
    private val onItemClick: (PokemonEntity) -> Unit
) : ListAdapter<PokemonEntity, PokemonAdapter.PokemonViewHolder>(DiffCallback()) {

    /**
     * Crea un nuevo [PokemonViewHolder] inflando el layout del ítem.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    /**
     * Vincula los datos del Pokémon al ViewHolder correspondiente.
     */
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder que representa un ítem de Pokémon en la lista.
     *
     * @property binding Binding del layout del ítem.
     */
    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Vincula los datos del Pokémon a las vistas y configura el listener de clic.
         *
         * @param pokemon Entidad Pokémon a mostrar.
         */
        fun bind(pokemon: PokemonEntity) {
            binding.tvName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            Glide.with(binding.ivPokemon.context).load(pokemon.imageUrl).into(binding.ivPokemon)
            binding.root.setOnClickListener { onItemClick(pokemon) }
        }
    }

    /**
     * Callback para [DiffUtil] que optimiza la actualización de la lista.
     */
    class DiffCallback : DiffUtil.ItemCallback<PokemonEntity>() {

        /**
         * Determina si dos ítems representan el mismo Pokémon (por ID).
         */
        override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity) = oldItem.id == newItem.id

        /**
         * Determina si el contenido de dos ítems es el mismo.
         */
        override fun areContentsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity) = oldItem == newItem
    }
}

package com.bimbo.android.utils.enum

import androidx.annotation.DrawableRes;
import com.bimbo.android.R

enum class PokemonType(
    /**
     * Obtiene el recurso drawable asociado al tipo de Pokémon.
     *
     * @return ID del recurso drawable
     */
    @param:DrawableRes val drawableRes: Int
) {
    FIRE(R.drawable.bg_type_fire),
    WATER(R.drawable.bg_type_water),
    GRASS(R.drawable.bg_type_grass),
    ELECTRIC(R.drawable.bg_type_electric),

    // Agrega más tipos según necesites
    UNKNOWN(R.drawable.bg_type_water); // Drawable por defecto

    companion object {
        /**
         * Obtiene el enum PokemonType correspondiente a la cadena dada.
         * Realiza comparación ignorando mayúsculas/minúsculas.
         * Si no encuentra coincidencia, devuelve UNKNOWN.
         *
         * @param type Nombre del tipo como String
         * @return PokemonType correspondiente o UNKNOWN si no existe
         */
        fun from(type: String?): PokemonType {
            if (type == null) {
                return UNKNOWN
            }
            for (pokemonType in entries) {
                if (pokemonType.name.equals(type, ignoreCase = true)) {
                    return pokemonType
                }
            }
            return UNKNOWN
        }
    }
}
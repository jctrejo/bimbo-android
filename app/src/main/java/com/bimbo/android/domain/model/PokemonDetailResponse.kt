package com.bimbo.android.domain.model

import com.bimbo.android.common.Constants.EMPTY

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val description: String? = EMPTY
)

data class TypeSlot(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String,
    val url: String
)

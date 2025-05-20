package com.bimbo.android.domain.model

data class PokemonDto(
    val id: Int,
    val name: String,
    val type: String
) {
    fun toDomain() = Pokemon(id, name, type)
}
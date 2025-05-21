package com.bimbo.android.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bimbo.android.common.Constants.DB_TABLE_POKEMON

@Entity(tableName = DB_TABLE_POKEMON)
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)

package com.bimbo.android.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bimbo.android.common.Constants.DB_POKEMON
import com.bimbo.android.common.Constants.URL_BASE
import com.bimbo.android.data.api.PokeApiService
import com.bimbo.android.data.db.PokemonDatabase
import com.bimbo.android.data.db.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providePokeApiService(retrofit: Retrofit): PokeApiService =
        retrofit.create(PokeApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PokemonDatabase =
        Room.databaseBuilder(context, PokemonDatabase::class.java, DB_POKEMON).build()

    @Provides
    @Singleton
    fun providePokemonDao(db: PokemonDatabase): PokemonDao = db.pokemonDao()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
}

package com.bimbo.android.data.repository

import android.content.SharedPreferences
import com.bimbo.android.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserRepository {
    private val keyUser = "usuario"

    override suspend fun getUserName(): String? = sharedPreferences.getString(keyUser, null)

    override suspend fun saveUserName(name: String) {
        sharedPreferences.edit().putString(keyUser, name).apply()
    }
}

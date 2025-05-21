package com.bimbo.android.domain.repository

interface UserRepository {
    suspend fun saveUserName(name: String)
}

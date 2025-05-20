package com.bimbo.android.domain.repository

interface UserRepository {
    suspend fun getUserName(): String?
    suspend fun saveUserName(name: String)
}
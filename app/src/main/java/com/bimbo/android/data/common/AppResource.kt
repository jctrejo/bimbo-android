package com.bimbo.android.data.common

sealed class AppResource<out T> {
    object Loading : AppResource<Nothing>()
    data class Success<T>(val item: T) : AppResource<T>()
    data class Error<T>(val throwable: String) : AppResource<T>()
}

package com.mobile.data.usage.Domain.Model

sealed class DataStateDomain<T> {
    data class Content<T>(val data: T) : DataStateDomain<T>()
    data class Error<T>(val e: Throwable) : DataStateDomain<T>()
    data class NetworkError<T>(val message: String) : DataStateDomain<T>()
    class Empty<T> : DataStateDomain<T>()
}
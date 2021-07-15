package com.africanbongo.whipitkotlin.ui.util

/**
 * Can be used in collecting the request from a data source.
 */
sealed class FetchResult<out T>{

    /**
     * Infer that the request was a success and the value is held here.
     */
    data class Success<T>(val data: T): FetchResult<T>()

    /**
     * Holds the exception in case an errors occurs in fetching the data.
     * Using Nothing as T makes sure that [Error] instances are a subtype of all Request<T>.
     */
    data class Error(val message: String) : FetchResult<Nothing>()

    /**
     * Indicate the data is loading.
     * Using Nothing as T makes sure that [Error] instances are a subtype of all Request<T>.
     */
    object Loading : FetchResult<Nothing>()


    companion object {
        fun <T> success(data: T): Success<T> = Success(data)
        fun error(message: String): Error = Error(message)
    }
}
package com.africanbongo.spoonacularandroid.service

/**
 * The expected number of recipes to be returned from a query.
 */
enum class QueryNumber(val value: Int) {
    SMALL(10), MEDIUM(25), LARGE(50), CRAZY(100)
}
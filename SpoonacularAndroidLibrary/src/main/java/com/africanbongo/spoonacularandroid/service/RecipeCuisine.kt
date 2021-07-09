package com.africanbongo.spoonacularandroid.service

/**
 * An enum class illustrating the cuisine that a recipe would belong to,
 * e.g. A Texas BBQ Medley is considered to be part of American cuisine.
 */
enum class RecipeCuisine(val type: String) {
    ALL("all"),
    AMERICAN("american"),
    BRITISH("british"),
    CHINESE("chinese"),
    FRENCH("french"),
    MEXICAN("mexican"),
    ITALIAN("italian"),
    INDIAN("indian"),
    GREEK("greek"),
    KOREAN("korean"),
    MEDITERRANEAN("mediterranean")
}
package com.africanbongo.whipitkotlin.network.model

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
    INDIAN("indian")
}

fun List<RecipeCuisine>.asStrings(): List<String> =
    map { cuisine ->
        cuisine.type.replaceFirstChar { it.titlecase() }
    }
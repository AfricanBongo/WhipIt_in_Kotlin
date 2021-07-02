package com.africanbongo.whipitkotlin.network

import com.africanbongo.whipitkotlin.model.Recipe
import com.squareup.moshi.Json

/**
 * Wrapper class to hold list of recipes returned from API.
 */
data class RecipeList(
    @Json(name = "recipes") val listOfRecipes: List<Recipe>
)
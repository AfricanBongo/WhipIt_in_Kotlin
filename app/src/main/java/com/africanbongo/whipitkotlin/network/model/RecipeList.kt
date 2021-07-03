package com.africanbongo.whipitkotlin.network.model

import com.africanbongo.whipitkotlin.network.model.Recipe
import com.squareup.moshi.Json

/**
 * Wrapper class to hold list of recipes returned from API.
 */
data class RecipeList(
    @Json(name = "recipes") val listOfRecipes: List<Recipe>
)
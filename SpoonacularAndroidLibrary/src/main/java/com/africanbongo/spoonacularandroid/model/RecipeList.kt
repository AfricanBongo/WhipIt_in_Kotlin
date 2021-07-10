package com.africanbongo.spoonacularandroid.model

import com.squareup.moshi.Json

/**
 * Container class to hold list of recipes returned from API.
 */
data class RecipeList(
    @Json(name = "recipes") val listOfRecipes: List<Recipe>
)
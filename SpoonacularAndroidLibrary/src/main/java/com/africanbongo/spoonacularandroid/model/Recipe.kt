package com.africanbongo.spoonacularandroid.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Network model object that represents the recipe stored within the Spoonacular API.
 * Use the [getInstructions] method when you want to access the [Steps] object, not the [steps] list.
 *
 * The [instructions] list is used to contain just the one object because the "analyzedInstructions" name
 * contains a Json array value with just one Json object, i.e. the [Steps] object.
 *
 * @author [Donell Mtabvuri (AfricanBongo)](https://github.com/AfricanBongo)
 */
@JsonClass(generateAdapter = true)
data class Recipe (
    val id: Int,
    val title: String,
    val image: String?,
    val servings: Int,
    val readyInMinutes: Int,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Float,
    val healthScore: Int,
    val weightWatcherSmartPoints: Int,
    val vegetarian: Boolean?,
    val vegan: Boolean?,
    val glutenFree: Boolean?,
    val dairyFree: Boolean?,
    val veryHealthy: Boolean?,
    val cheap: Boolean?,
    val veryPopular: Boolean?,

    /**
     * The types of dishes this recipe belongs to,
     * i.e. when it's served, or where, e.g. "breakfast", "main course"
     * The dishes are always in lowercase.
     */
    val dishTypes: List<String>?,

    /**
     * The ingredients of the recipe in more detail.
     */
    @Json(name = "extendedIngredients") val ingredients: List<Ingredient>?,

    /**
     * The analyzed instructions containing in-depth information about the steps to take in making the recipe
     */
    @Json(name = "analyzedInstructions") val instructions: List<Steps>?
) {
    /**
     * Get the [Steps] object with the list of steps used in the recipe.
     */
    fun getInstructions() = instructions?.first()
}
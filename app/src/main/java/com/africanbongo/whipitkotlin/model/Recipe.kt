package com.africanbongo.whipitkotlin.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import android.os.Bundle

/**
 * Data holder class for basic information about a recipe.
 * The JSON object representation of a recipe is mapped from the host API using the Moshi library.
 * The [Recipe] object implements the [Parcelable] interface, so it is easy to pass around enclosed within a [Bundle].
 * The recipe information is obtained from the [Spoonacular API](https://spoonacular.com/food-api).
 */
@Parcelize
data class Recipe(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "sourceName") val sourceName: String?,
    @Json(name = "image") val imgSrcUrl: String?,
    @Json(name = "servings") val servings: Int,
    @Json(name = "readyInMinutes") val readyInMinutes: Int,
    @Json(name = "spoonacularScore") val spoonacularScore: Double,
    @Json(name = "glutenFree") val isGlutenFree: Boolean?,
    @Json(name = "vegetarian") val isVegetarian: Boolean?,
    @Json(name = "vegan") val isVegan: Boolean?,
    @Json(name = "ketogenic") val isKetogenic: Boolean?) : Parcelable
package com.africanbongo.spoonacularandroid.model

import com.squareup.moshi.JsonClass

/**
 * An ingredient used in a recipe.
 */
@JsonClass(generateAdapter = true)
data class Ingredient (
    /**
     * The description of the ingredient without any amount or unit attached to it.
     * E.g. "clove(s) of garlic"
     */
    val originalName: String,

    /**
     * The amount of the ingredient to be used.
     * E.g. "2"
     */
    val amount: Float?,

    /**
     * The unit of measurement for the ingredient.
     * E.g. "g" for "grams"
     */
    val unit: String?
)
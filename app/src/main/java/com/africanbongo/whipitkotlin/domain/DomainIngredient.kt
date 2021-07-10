package com.africanbongo.whipitkotlin.domain

import com.africanbongo.spoonacularandroid.model.Ingredient

/**
 * Domain object of the network [Ingredient] object.
 * @see Ingredient
 */
data class DomainIngredient (
    val description: String,
    val amount: Float,
    val unit: String?
)
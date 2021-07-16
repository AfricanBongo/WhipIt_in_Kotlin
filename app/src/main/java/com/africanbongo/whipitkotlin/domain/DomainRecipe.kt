package com.africanbongo.whipitkotlin.domain

import java.util.*

open class SummarisedRecipe(
    open val id: Int,
    open val title: String,
    open val imageUrl: String?,
    open val sourceName: String,
    open val spoonacularScore: Double,
    open val servings: Int,
    open val readyInMinutes: Int
) {
    /**
     * Get the preparation time in quantified String format, e.g. 30min.
     */
    fun getPrepTime(): String = "${readyInMinutes}min"

    /**
     * Get the [spoonacularScore] as String rating in the range 0 to 5.
     */
    fun getRating(): String {
        val rating = spoonacularScore * 0.05
        return String.format(Locale.US, "%.1f", rating)
    }
}

data class FullRecipe(
    override val id: Int,
    override val title: String,
    override val imageUrl: String?,
    override val sourceName: String,
    val sourceUrl: String,
    override val spoonacularScore: Double,
    override val servings: Int,
    override val readyInMinutes: Int,
    val cuisine: String,
    val ingredients: List<DomainIngredient>?,
    val steps: List<DomainInstruction>?
) : SummarisedRecipe(
    id, title, imageUrl, sourceName, spoonacularScore, servings, readyInMinutes
)


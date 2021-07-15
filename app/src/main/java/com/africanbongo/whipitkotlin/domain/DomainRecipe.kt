package com.africanbongo.whipitkotlin.domain

data class DomainRecipe(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Double,
    val servings: Int,
    val readyInMinutes: Int,
    val cuisine: String,
    val ingredients: List<DomainIngredient>?,
    val steps: List<DomainInstruction>?
)

data class SummarisedRecipe(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val sourceName: String,
    val spoonacularScore: Double,
    val servings: Int,
    val readyInMinutes: Int
) {
    fun getPrepTime(): String = "${readyInMinutes}min"
}
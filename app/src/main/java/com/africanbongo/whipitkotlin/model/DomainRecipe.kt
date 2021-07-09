package com.africanbongo.whipitkotlin.model

data class DomainRecipe (
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Float,
    val servings: Int,
    val readyInMinutes: Int,
    val ingredients: List<DomainIngredient>?,
    val steps: List<DomainInstruction>?
)

data class SummarisedRecipe(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val sourceName: String,
    val spoonacularScore: Float,
    val servings: Int,
    val readyInMinutes: Int
)
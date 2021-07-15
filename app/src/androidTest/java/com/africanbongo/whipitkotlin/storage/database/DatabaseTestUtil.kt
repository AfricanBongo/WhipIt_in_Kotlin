package com.africanbongo.whipitkotlin.storage.database

import com.africanbongo.whipitkotlin.storage.database.model.DatabaseRecipe

object DatabaseTestUtil {

    private val recipeList = listOf(
        1 to "Soup", 2 to "Sauce", 3 to "Pasta", 4 to "Beef", 5 to "Gravy"
    )

    private val ingredientList = listOf(
        1 to "Salt", 2 to "Sugar", 3 to "Flour", 4 to "Pepper", 5 to "Beef"
    )

    fun createRecipe(number: Int): List<DatabaseRecipe> {
        val recipes = mutableListOf<DatabaseRecipe>()

        for (i in 0 until number) {
            val someRecipeDetails = recipeList[i]
            recipes.add(
                DatabaseRecipe(
                    recipeId = someRecipeDetails.first,
                    title = someRecipeDetails.second,
                    imageUrl = "sauce.com",
                    sourceName = "Donell Mtabvuri",
                    sourceUrl = "sauce.com",
                    spoonacularScore = 45.8,
                    servings = 3,
                    readyInMinutes = 45
            ))
        }

        return recipes
    }
}
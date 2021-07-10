package com.africanbongo.whipitkotlin.storage.database.model

import com.africanbongo.spoonacularandroid.model.*

/**
 * Map a list of [Ingredient] into a list of [DatabaseIngredient].
 * @return Mapped list of [DatabaseIngredient].
 */
private fun List<Ingredient>.toDatabaseModels(): List<DatabaseIngredient> = map { ingredient ->
    DatabaseIngredient(
        ingredientId = ingredient.id,
        description = ingredient.originalName,
        amount = ingredient.amount,
        unit = ingredient.unit
    )
}

/**
 * Map a list of [Instruction] into a list of [DatabaseInstruction].
 * @return Mapped list of [DatabaseInstruction].
 */
@JvmName("toDatabaseModelsInstruction")
private fun List<Instruction>.toDatabaseModels(recipeId: Int) = map { instruction ->
    DatabaseInstruction(
        recipeId = recipeId,
        number = instruction.number,
        step = instruction.step
    )
}

/**
 * Maps a list of [CuisineEnum] into a list of [DatabaseCuisine].
 */
@JvmName("toDatabaseModelsCuisineEnum")
private fun List<CuisineEnum>?.toDatabaseModels(): List<DatabaseCuisine>? = this?.map { enum ->
    DatabaseCuisine(cuisineId = enum.id, type = enum.type)
}

/**
 * Parse a network model [Recipe] into:
 * - [DatabaseRecipe],
 * - list of [DatabaseIngredient]s,
 * - list of [DatabaseInstruction]s,
 * - list of [DatabaseCuisine]s
 * @return a compound [Pair] of:
 * - a pair of a [DatabaseRecipe] and list of [DatabaseIngredient]s and,
 * - a pair of a list of [DatabaseInstruction]s and a list of [DatabaseCuisine]s
 */
fun Recipe.decompose(): Pair<
        Pair<DatabaseRecipe, List<DatabaseIngredient>>,
        Pair<List<DatabaseInstruction>?, List<DatabaseCuisine>?>
        > {
    val databaseRecipe = DatabaseRecipe(
        recipeId = id,
        title = title,
        imageUrl = image,
        sourceName = sourceName,
        sourceUrl = sourceUrl,
        spoonacularScore = spoonacularScore,
        servings = servings,
        readyInMinutes = readyInMinutes
    )

    val listOfIngredients = ingredients.toDatabaseModels()
    val listOfInstructions = getInstructions()?.listOfInstructions?.toDatabaseModels(id)
    val listOfCuisines = cuisine.toCuisineEnums().toDatabaseModels()

    return (databaseRecipe to listOfIngredients) to
            (listOfInstructions to listOfCuisines)
}
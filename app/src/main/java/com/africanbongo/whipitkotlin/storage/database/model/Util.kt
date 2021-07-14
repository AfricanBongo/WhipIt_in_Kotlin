package com.africanbongo.whipitkotlin.storage.database.model

import zw.co.bitpirates.spoonacularclient.model.*

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
 * @param recipeId The id of the recipe the instructions belong to.
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
 * Parse a network model [Recipe] into a [DecomposedBundle].
 * @return A [DecomposedBundle].
 * @see DecomposedBundle
 */
fun Recipe.decompose(): DecomposedBundle {
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

    return DecomposedBundle(
        databaseRecipe to listOfIngredients,
        listOfInstructions to listOfCuisines)
}

/**
 * Transform a list of [Recipe] into a list of [DecomposedBundle]s ready to be saved into the database.
 */
fun List<Recipe>.decompose(): List<DecomposedBundle> = map { it.decompose() }

/**
 * Use a [DatabaseCuisine] and [recipeId] to create a [RecipeCuisineCrossRef].
 * @param recipeId The recipe id of a recipe in this certain cuisine.
 */
fun DatabaseCuisine.generateCrossRef(recipeId: Int) =
    RecipeCuisineCrossRef(recipeId, cuisineId)

/**
 * Generate a list of cross reference database objects of a cuisine and a recipe.
 * @param recipeId The id of the recipe that has the list of cuisines it belongs to.
 */
@JvmName("generateCrossRefsDatabaseCuisine")
fun List<DatabaseCuisine>?.generateCrossRefs(recipeId: Int) = this?.map {
    it.generateCrossRef(recipeId)
}

/**
 * Use a [DatabaseIngredient] and [recipeId] to create a [RecipeIngredientCrossRef].
 * @param recipeId The recipe id of a recipe in this certain cuisine.
 */
fun DatabaseIngredient.generateCrossRef(recipeId: Int): RecipeIngredientCrossRef =
    RecipeIngredientCrossRef(recipeId, ingredientId)

/**
 * Generate a list of cross reference database objects of an ingredient and a recipe.
 * @param recipeId The id of the recipe that has the list of ingredients it belongs to.
 */
fun List<DatabaseIngredient>.generateCrossRefs(recipeId: Int): List<RecipeIngredientCrossRef> = map {
    it.generateCrossRef(recipeId)
}

/**
 * A compound [Pair] of:
 * - a pair of a [DatabaseRecipe] and list of [DatabaseIngredient]s and,
 * - a pair of a list of [DatabaseInstruction]s and a list of [DatabaseCuisine]s
 */
typealias DecomposedBundle = Pair <
        Pair<DatabaseRecipe, List<DatabaseIngredient>>,
        Pair<List<DatabaseInstruction>?, List<DatabaseCuisine>?>
        >

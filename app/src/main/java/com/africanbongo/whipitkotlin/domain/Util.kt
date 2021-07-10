package com.africanbongo.whipitkotlin.domain

import com.africanbongo.whipitkotlin.storage.database.model.DatabaseCuisine
import com.africanbongo.whipitkotlin.storage.database.model.DatabaseIngredient
import com.africanbongo.whipitkotlin.storage.database.model.DatabaseInstruction
import com.africanbongo.whipitkotlin.storage.database.model.DatabaseRecipe

///**
// * Convert a network model of [Ingredient] into the domain equivalent.
// * @return Domain specific model mapped from the [Ingredient] model.
// */
//fun Ingredient.toDomainModel(): DomainIngredient =
//    DomainIngredient(
//        description = nameClean,
//        amount = amount,
//        unit = unit
//    )
//
///**
// * Convert a network model of [Steps] into a list of [DomainInstruction]s.
// * @return Domain specific model mapped from the [Steps] model.
// */
//fun Steps.toDomainModel(): List<DomainInstruction> = this.let {
//    it.listOfInstructions.map { instruction ->
//        DomainInstruction(instruction.number, instruction.step)
//    }
//}
//
///**
// * Convert a network model of [Recipe] into the domain equivalent.
// * @return Domain specific model mapped from the [Recipe] model.
// */
//fun Recipe.toDomainModel(): DomainRecipe =
//    DomainRecipe(
//        id = id,
//        title = title,
//        imageUrl = image,
//        sourceName = sourceName,
//        sourceUrl = sourceUrl,
//        spoonacularScore = spoonacularScore,
//        servings = servings,
//        readyInMinutes = readyInMinutes,
//        ingredients = ingredients?.map { it.toDomainModel() },
//        // TODO Fix cuisine problem here.
//        steps = getInstructions()?.toDomainModel()
//    )

/**
 * Truncate a [DomainRecipe] into a [SummarisedRecipe]
 * @return [SummarisedRecipe] mapped from the [DomainRecipe] model.
 */
fun DomainRecipe.toSummarisedRecipe(): SummarisedRecipe =
    SummarisedRecipe(
        id = id,
        title = title,
        imageUrl = imageUrl,
        sourceName = sourceName,
        spoonacularScore = spoonacularScore,
        servings = servings,
        readyInMinutes = readyInMinutes
    )

/**
 * Map a list of [DatabaseIngredient]s into a list of [DomainIngredient]s.
 * @return A mapped list of [DomainIngredient].
 */
private fun List<DatabaseIngredient>.toDomainModels(): List<DomainIngredient> = map { databaseIngredient ->
        DomainIngredient(
            description = databaseIngredient.description,
            amount = databaseIngredient.amount,
            unit = databaseIngredient.unit
        )
    }

/**
 * Map a list of [DatabaseInstruction]s into a list of [DomainInstruction]s.
 * @return A mapped list of [DomainInstruction]s.
 */
@JvmName("toDomainModelsDatabaseInstruction")
private fun List<DatabaseInstruction>.toDomainModels(): List<DomainInstruction> = map { databaseInstruction ->
    DomainInstruction(
        number = databaseInstruction.number,
        step = databaseInstruction.step
    )
}.sortedBy { it.number }

/**
 * Map a [DatabaseRecipe] object into a [DomainRecipe] object
 * Combine with [DatabaseIngredient]s and [DatabaseInstruction]s into one object.
 * @param ingredients List of ingredients fetched from the database.
 * @param instructions List of instructions fetched from the database.
 * @return A mapped [DomainRecipe].
 */
fun DatabaseRecipe.toDomainModel(
    cuisine: DatabaseCuisine,
    ingredients: List<DatabaseIngredient>,
    instructions: List<DatabaseInstruction>): DomainRecipe =
    DomainRecipe(
        id = recipeId,
        title = title,
        imageUrl = imageUrl,
        sourceName = sourceName,
        sourceUrl = sourceUrl,
        spoonacularScore = spoonacularScore,
        servings = servings,
        readyInMinutes = readyInMinutes,
        cuisine = cuisine.type,
        ingredients = ingredients.toDomainModels(),
        steps = instructions.toDomainModels(),
    )


package com.africanbongo.whipitkotlin.model

import com.africanbongo.spoonacularandroid.model.Ingredient
import com.africanbongo.spoonacularandroid.model.Recipe
import com.africanbongo.spoonacularandroid.model.Steps

/**
 * Convert a network model of [Ingredient] into the domain equivalent.
 * @return Domain specific model mapped from the [Ingredient] model.
 */
fun Ingredient.toDomainModel(): DomainIngredient =
    DomainIngredient(
        description = nameClean,
        amount = amount,
        unit = unit
    )

/**
 * Convert a network model of [Steps] into a list of [DomainInstruction]s.
 * @return Domain specific model mapped from the [Steps] model.
 */
fun Steps.toDomainModel(): List<DomainInstruction> = this.let {
    it.listOfInstructions.map { instruction ->
        DomainInstruction(instruction.number, instruction.step)
    }
}

/**
 * Convert a network model of [Recipe] into the domain equivalent.
 * @return Domain specific model mapped from the [Recipe] model.
 */
fun Recipe.toDomainModel(): DomainRecipe =
    DomainRecipe(
        id = id,
        title = title,
        imageUrl = image,
        sourceName = sourceName,
        sourceUrl = sourceUrl,
        spoonacularScore = spoonacularScore,
        servings = servings,
        readyInMinutes = readyInMinutes,
        ingredients = ingredients?.map { it.toDomainModel() },
        steps = getInstructions()?.toDomainModel()
    )

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
package com.africanbongo.whipitkotlin.storage.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import zw.co.bitpirates.spoonacularclient.model.*

/**
 * Class used to cross-reference the [DatabaseRecipe] and [DatabaseIngredient] relations in the database.
 */
@Entity(primaryKeys = ["recipeId", "ingredientId"])
class RecipeIngredientCrossRef (
    val recipeId: Int,
    val ingredientId: Int
)

/**
 * Class used to cross-reference the [DatabaseRecipe] and [DatabaseCuisine] relations in the database.
 */
@Entity(primaryKeys = ["recipeId", "cuisineId"])
class RecipeCuisineCrossRef (
    val recipeId: Int,
    val cuisineId: Int,
)

/**
 * Container class that holds a [DatabaseRecipe] and a list of [DatabaseIngredient]s, fetched from the database.
 */
data class RecipeWithIngredients (
    @Embedded val recipe: DatabaseRecipe,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "ingredientId",
        associateBy = Junction(RecipeIngredientCrossRef::class)
    )
    val ingredients: List<DatabaseIngredient>
)

/**
 * Container class that holds a [CuisineEnum] and a list of [DatabaseRecipe] IDs, fetched from the database.
 */
data class CuisineWithRecipeIds (
    @Embedded val cuisine: DatabaseCuisine,
    @Relation(
        entity= DatabaseRecipe::class,
        parentColumn = "cuisineId",
        entityColumn = "recipeId",
        associateBy = Junction(RecipeCuisineCrossRef::class),
        projection = ["recipeId"]
    )
    val recipes: List<Int>
)
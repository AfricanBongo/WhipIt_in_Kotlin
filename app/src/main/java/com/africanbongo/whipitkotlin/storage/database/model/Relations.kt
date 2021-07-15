package com.africanbongo.whipitkotlin.storage.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import zw.co.bitpirates.spoonacularclient.model.*

/**
 * Class used to cross-reference the [DatabaseRecipe] and [DatabaseIngredient] relations in the database.
 */
@Entity(primaryKeys = ["recipeId", "ingredientId"], tableName = "ri_table")
class RecipeIngredientCrossRef (
    val recipeId: Int,
    val ingredientId: Int
)

/**
 * Class used to cross-reference the [DatabaseRecipe] and [DatabaseCuisine] relations in the database.
 */
@Entity(primaryKeys = ["recipeId", "cuisineId"], tableName = "rc_table")
class RecipeCuisineCrossRef (
    val recipeId: Int,
    val cuisineId: Int,
)
package com.africanbongo.whipitkotlin.storage.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.africanbongo.spoonacularandroid.model.Ingredient
import com.africanbongo.spoonacularandroid.model.Recipe

/**
 * Class used to cross-reference the [DatabaseRecipe] and [DatabaseIngredient] relations in the database.
 */
@Entity(primaryKeys = ["recipeId", "ingredientId"])
class RecipeIngredientCrossRef (
    val recipeId: Int,
    val ingredientId: Int
)

/**
 * Container class that holds a [Recipe] and a list of [Ingredient]s, fetched from the database.
 */
data class RecipeWithIngredients (
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "ingredientId",
        associateBy = Junction(RecipeIngredientCrossRef::class)
    )
    val ingredients: List<Ingredient>
)
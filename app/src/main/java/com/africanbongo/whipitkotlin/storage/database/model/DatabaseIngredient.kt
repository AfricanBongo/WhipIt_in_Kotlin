package com.africanbongo.whipitkotlin.storage.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.africanbongo.spoonacularandroid.model.Ingredient

/**
 * Mapping of an [Ingredient] that will be stored in the database.
 */
@Entity(tableName = "ingredient_table")
data class DatabaseIngredient (
    @PrimaryKey(autoGenerate = false) val ingredientId: Int,
    val description: String,
    val amount: Float,
    val unit: String?
)
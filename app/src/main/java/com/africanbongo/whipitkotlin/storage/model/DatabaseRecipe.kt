package com.africanbongo.whipitkotlin.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Recipe class that's used to store and retrieve objects in the database.
 */
@Entity(tableName = "recipe_table")
data class DatabaseRecipe (
   @PrimaryKey val recipeId: Int,
   val title: String,
   val imageUrl: String?,
   val sourceName: String,
   val spoonacularScore: Float,
   val servings: Int,
   val readyInMinutes: Int,
)
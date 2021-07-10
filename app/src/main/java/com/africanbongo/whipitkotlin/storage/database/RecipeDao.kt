package com.africanbongo.whipitkotlin.storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.africanbongo.spoonacularandroid.model.Ingredient
import com.africanbongo.whipitkotlin.storage.database.model.*

/**
 * Data Access Object to manipulate the database that holds the following:
 * - [DatabaseRecipe],
 * - [DatabaseIngredient],
 * - [DatabaseInstruction],
 * - [DatabaseCuisine]
 */
@Dao
interface RecipeDao {

    /**
     * Insert a recipe into the database.
     * If a copy is already in the database, replace it.
     * @param recipe The recipe to be put into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: DatabaseRecipe)

    /**
     * Insert a list of ingredients into the database.
     * If a copy of an ingredient is already in the database, replace it.
     * @param ingredients The list of ingredients to insert into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<Ingredient>)

    /**
     * Insert a list of instructions into the database.
     * If a copy of an instruction is already in the database, replace it.
     * @param instructions The list of ingredients to insert into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstructions(instructions: List<DatabaseInstruction>?)

    /**
     * Insert a list of cuisines into the database.
     * If a copy of an cuisines is already in the database, replace it.
     * @param cuisines The list of ingredients to insert into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCuisines(cuisines: List<DatabaseCuisine>?)

    // TODO Finish these methods.
//    @Query("SELECT * FROM recipe_table WHERE ")
//    suspend fun getCuisineWithRecipes()
}
package com.africanbongo.whipitkotlin.storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.africanbongo.whipitkotlin.storage.database.model.*
import kotlinx.coroutines.flow.Flow

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
    suspend fun insertIngredients(ingredients: List<DatabaseIngredient>)

    /**
     * Insert a list of instructions into the database.
     * If a copy of an instruction is already in the database, replace it.
     * @param instructions The list of ingredients to insert into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstructions(instructions: List<DatabaseInstruction>)

    /**
     * Insert a list of cuisines into the database.
     * If a copy of an cuisines is already in the database, replace it.
     * @param cuisines The list of ingredients to insert into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCuisines(cuisines: List<DatabaseCuisine>)

    /**
     * Insert a list of recipe id's paired with ingredient ids into the database.
     * @param list List of [RecipeCuisineCrossRef].
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeIngredientCrossRefs(list: List<RecipeIngredientCrossRef>)

    /**
     * Insert a list of recipe id's paired with cuisine id's into the database.
     * @param list List of [RecipeCuisineCrossRef].
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeCuisineCrossRef(list: List<RecipeCuisineCrossRef>)

    /**
     * Fetch all the recipes from the database.
     */
    @Query("SELECT * FROM recipe_table ORDER BY RANDOM() LIMIT 20")
    suspend fun getAllRecipes(): List<DatabaseRecipe>

    /**
     * Retrieve recipe having [DatabaseRecipe.spoonacularScore] greater than or equal to [score].
     * @param score The score that the recipes returned should be or above of.
     * @return A list of recipes.
     */
    @Query("SELECT * FROM recipe_table WHERE spoonacularScore >= :score ORDER BY spoonacularScore DESC LIMIT 10")
    fun getRecipesOfScore(score: Double): Flow<List<DatabaseRecipe>>

    /**
     * Fetch the associated recipes of a cuisine.
     * @param cuisineId The id used to locate the recipes to return.
     * @return A list of recipes.
     */
    @Query("SELECT * FROM recipe_table LEFT JOIN rc_table ON recipe_table.recipeId=rc_table.recipeId WHERE cuisineId = :cuisineId ORDER BY RANDOM() LIMIT 20")
    suspend fun getRecipesOfCuisine(cuisineId: Int): List<DatabaseRecipe>

    /**
     * Check if recipes of this cuisine exist.
     * @return 1, if they exist, else 0 if none exist
     */
    @Query("SELECT COUNT(*) FROM recipe_table LEFT JOIN rc_table ON recipe_table.recipeId=rc_table.recipeId WHERE cuisineId = :cuisineId LIMIT 1")
    suspend fun recipesExistOfCuisine(cuisineId: Int): Int

    /**
     * Check if there are any recipes at all.
     * @return 1, if they exist, else 0 if none exist
     */
    @Query("SELECT COUNT(*) FROM recipe_table LIMIT 1")
    suspend fun recipesExist(): Int

    /**
     * Fetch the associated ingredients of a recipe.
     * @param recipeId The id used to locate the recipe to return.
     * @return A list of ingredients.
     */
    @Query("SELECT * FROM ingredient_table LEFT JOIN ri_table ON ingredient_table.ingredientId=ri_table.ingredientId WHERE recipeId = :recipeId")
    suspend fun getIngredientsOfRecipe(recipeId: Int): List<DatabaseIngredient>

    /**
     * Fetch the instructions pertaining to a specific recipe.
     * @param recipeId The id used to locate the instructions to return.
     * @return A list of instructions.
     */
    @Query("SELECT * FROM instruction_table WHERE recipeId = :recipeId")
    suspend fun getInstructionsOfRecipe(recipeId: Int): List<DatabaseInstruction>?

}
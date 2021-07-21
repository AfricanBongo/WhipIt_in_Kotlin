package com.africanbongo.whipitkotlin.storage.repository

import com.africanbongo.whipitkotlin.domain.*
import com.africanbongo.whipitkotlin.storage.database.RecipeDao
import com.africanbongo.whipitkotlin.storage.database.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import zw.co.bitpirates.spoonacularclient.model.CuisineEnum
import zw.co.bitpirates.spoonacularclient.service.SpoonacularApi

/**
 * Access data about the recipes.
 * Caches the recipes in memory just in case of a network failure.
 * @param recipeDao The Dao used to fetch data.
 */
class RecipeRepository(private val recipeDao: RecipeDao) {

    private var _summarisedRecipesList = MutableStateFlow<List<SummarisedRecipe>>(emptyList())

    /**
     * A list of summarised recipes fetched from storage.
     * @see SummarisedRecipe
     */
    val summarisedRecipeList: StateFlow<List<SummarisedRecipe>> = _summarisedRecipesList

    /**
     * Recipes with high ratings that can be used for the recipe of the day.
     */
    val recipesOfTheDay: Flow<List<SummarisedRecipe>> = recipeDao.getRecipesOfScore((80).toDouble()).map { recipes ->
        return@map recipes.map {
            it.toSummarisedRecipe()
        }
    }

    /**
     * Refresh the cache containing the list of recipes of a certain cuisine.
     */
    suspend fun refreshCacheFor(cuisine: CuisineEnum) = withContext(Dispatchers.IO) {
        // Fetch the list of recipes from the network server asynchronously.
        // Parse it into DecomposedBundles and then store them in the database as an async task.

        val listOfRecipes = SpoonacularApi.getRecipesOfCuisine(cuisine)
        val listOfDecomposedBundles = listOfRecipes.decompose()
        listOfDecomposedBundles.forEach { decomposedBundle ->
            // Unpack all objects from the bundle.
            val recipe: DatabaseRecipe = decomposedBundle.first.first
            val listOfIngredients: List<DatabaseIngredient> = decomposedBundle.first.second
            val listOfInstruction: List<DatabaseInstruction>? = decomposedBundle.second.first
            val listOfCuisines: List<DatabaseCuisine>? = decomposedBundle.second.second

            // Generate database cross-reference objects
            val riCrossRefs: List<RecipeIngredientCrossRef> =
                listOfIngredients.generateCrossRefs(recipe.recipeId)
            val rcCrossRefs: List<RecipeCuisineCrossRef>? =
                listOfCuisines.generateCrossRefs(recipe.recipeId)

            // Insert the objects into the database.
            recipeDao.insertRecipe(recipe)
            recipeDao.insertIngredients(listOfIngredients)
            listOfInstruction?.let { recipeDao.insertInstructions(it) }
            listOfCuisines?.let { recipeDao.insertCuisines(it) }
            recipeDao.insertRecipeIngredientCrossRefs(riCrossRefs)
            rcCrossRefs?.let { recipeDao.insertRecipeCuisineCrossRef(it) }
        }

        _summarisedRecipesList.value =
            if (cuisine == CuisineEnum.NONE)
                recipeDao.getAllRecipes().map {
                    it.toSummarisedRecipe()
                }
            else
                recipeDao.getRecipesOfCuisine(cuisine.id).map {
                    it.toSummarisedRecipe()
                }
    }
}
package com.africanbongo.whipitkotlin.storage.database

import com.africanbongo.whipitkotlin.domain.DomainRecipe
import com.africanbongo.whipitkotlin.domain.toDomainModel
import com.africanbongo.whipitkotlin.storage.database.model.*
import com.africanbongo.whipitkotlin.ui.FetchResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import zw.co.bitpirates.spoonacularclient.exception.ServerException
import zw.co.bitpirates.spoonacularclient.model.CuisineEnum
import zw.co.bitpirates.spoonacularclient.service.SpoonacularApi

/**
 * Access data about the recipes.
 * Caches the recipes in memory just in case of a network failure.
 * @param recipeDao The Dao used to fetch data.
 * @param repositoryOwnerScope The coroutine scope the class will utilise for async tasks.
 */
class RecipeRepository(private val recipeDao: RecipeDao) {

    private var _recipeRequest: MutableStateFlow<FetchResult<List<DomainRecipe>>> = MutableStateFlow(FetchResult.Loading)

    /**
     * Result object wrapped within a [StateFlow].
     *
     * When the data request is made, the result is set to [FetchResult.Loading].
     *
     * If the data request is [FetchResult.Success], the result will contain the list of recipes.
     *
     * Else if unsuccessful it will contain the error message in a [FetchResult.Error] object.
     */
    val recipeResult: StateFlow<FetchResult<List<DomainRecipe>>> = _recipeRequest

    /**
     * Refresh the cache containing the list of recipes of a certain cuisine.
     * @param cuisine The type of cuisine the recipes in the cache should belong to.
     */
    suspend fun refreshCacheFor(cuisine: CuisineEnum) = withContext(Dispatchers.Default) {
        // Fetch the list of recipes from the network server asynchronously.
        // Parse it into DecomposedBundles and then store them in the database as an async task.
        _recipeRequest.value = FetchResult.Loading

        try {

            val listOfRecipes = SpoonacularApi.getRecipesOfCuisine(cuisine)
            val listOfDecomposedBundles = listOfRecipes.decompose()
            listOfDecomposedBundles.forEach { decomposedBundle ->
                // Unpack all objects from the bundle.
                val recipe: DatabaseRecipe = decomposedBundle.first.first
                val listOfIngredients: List<DatabaseIngredient> = decomposedBundle.first.second
                val listOfInstruction: List<DatabaseInstruction>? = decomposedBundle.second.first
                val listOfCuisines: List<DatabaseCuisine>? = decomposedBundle.second.second

                // Generate database cross-reference objects
                val riCrossRefs: List<RecipeIngredientCrossRef> = listOfIngredients.generateCrossRefs(recipe.recipeId)
                val rcCrossRefs: List<RecipeCuisineCrossRef>? = listOfCuisines.generateCrossRefs(recipe.recipeId)

                withContext(Dispatchers.IO) {
                    // Insert the objects into the database.
                    recipeDao.insertRecipe(recipe)
                    recipeDao.insertIngredients(listOfIngredients)
                    listOfInstruction?.let { recipeDao.insertInstructions(it) }
                    listOfCuisines?.let { recipeDao.insertCuisines(it) }
                    recipeDao.insertRecipeIngredientCrossRefs(riCrossRefs)
                    rcCrossRefs?.let { recipeDao.insertRecipeCuisineCrossRef(it) }

                    // Fetch results from the database.
                    // This is done here to fetch recipes that had been inserted before this run in a different cuisine.
                    // But are also part of the cuisine being requested for.
                    val cuisineWithRecipes = recipeDao.getCuisineWithRecipes(cuisine.id)
                    val certainCuisine = cuisineWithRecipes.cuisine
                    val recipes = cuisineWithRecipes.recipes.map {
                        val recipeWithIngredients = recipeDao.getRecipeWithIngredients(it)
                        val instructions = recipeDao.getInstructionsOfRecipe(it)
                        val databaseRecipe = recipeWithIngredients.recipe
                        val ingredients = recipeWithIngredients.ingredients
                        return@map databaseRecipe.toDomainModel(
                            cuisine = certainCuisine,
                            ingredients = ingredients,
                            instructions = instructions
                        )
                    }

                    _recipeRequest.value = FetchResult.success(recipes)
                }
            }
        } catch (e: ServerException) {
            _recipeRequest.value = FetchResult.error(e.message!!)
        }
    }
}
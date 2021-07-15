package com.africanbongo.whipitkotlin.storage.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.africanbongo.whipitkotlin.storage.database.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RecipeDatabaseTest {

    private lateinit var recipeDao: RecipeDao
    private lateinit var db: RecipeDatabase


    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RecipeDatabase::class.java
        ).build()
        recipeDao = db.recipeDao
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeRecipesAndReadThem() {
        val recipeList = DatabaseTestUtil.createRecipe(4)


        runBlocking {
            launch(Dispatchers.Main) {
                recipeList.forEach { recipeDao.insertRecipe(it) }

                val cuisine = DatabaseCuisine(1, "pasta")

                val recipesCuisines = mutableListOf<RecipeCuisineCrossRef>()
                recipeList.forEach {
                    recipesCuisines.add(RecipeCuisineCrossRef(it.recipeId, cuisine.cuisineId))
                }

                recipeDao.insertRecipeCuisineCrossRef(recipesCuisines)
                val crossRef = recipeDao.getRecipesOfCuisine(cuisine.cuisineId)
                assertNotNull(crossRef)
            }
        }

    }

    @Test
    fun writeIngredientsAndReadThem() {

        val recipe = DatabaseTestUtil.createRecipe(1).first()
        val ingredientList = listOf<DatabaseIngredient>(
            DatabaseIngredient(ingredientId = 1, description = "sugar", amount = 4.toDouble(), unit = "tsp")
        )

        runBlocking {
            launch(Dispatchers.Main) {
                recipeDao.insertIngredients(ingredientList)
                recipeDao.insertRecipeIngredientCrossRefs(
                    ingredientList.map { RecipeIngredientCrossRef(recipe.recipeId, it.ingredientId) }
                )

                assertNotNull(recipeDao.getIngredientsOfRecipe(recipe.recipeId))
            }
        }
    }
}
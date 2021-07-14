package com.africanbongo.whipitkotlin.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.africanbongo.whipitkotlin.storage.database.model.*

/**
 * Database that contains the tables of the following:
 * - [DatabaseRecipe],
 * - [DatabaseIngredient],
 * - [DatabaseInstruction],
 * - [DatabaseCuisine]
 */
@Database(
    entities = [
        DatabaseRecipe::class,
        DatabaseCuisine::class,
        DatabaseInstruction::class,
        DatabaseIngredient::class,
        RecipeIngredientCrossRef::class,
        RecipeCuisineCrossRef::class
               ],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {

    /**
     * Access, manage and manipulate the recipe components stored in the database.
     */
    abstract val recipeDao: RecipeDao

    companion object {
        @Volatile private var INSTANCE: RecipeDatabase? = null

        /**
         * Get the instance of the database.
         * If instance is null, a new instance will be created, saved and returned in the method call.
         * @return An instance of the recipe database.
         */
        fun getInstance(context: Context): RecipeDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }.also {
                INSTANCE = it
            }
    }
}
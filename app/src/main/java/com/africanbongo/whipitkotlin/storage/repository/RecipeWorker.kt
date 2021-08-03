package com.africanbongo.whipitkotlin.storage.repository

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.africanbongo.whipitkotlin.storage.database.RecipeDatabase
import zw.co.bitpirates.spoonacularclient.model.CuisineEnum

/**
 * This worker preloads a lot of recipes into the cache of the app.
 */
class RecipeWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        val repository = RecipeRepository(RecipeDatabase.getInstance(applicationContext).recipeDao)

        // Prefetch recipes for every cuisine.
        for (cuisine in CuisineEnum.values()) {
            repository.refreshCacheFor(cuisine, false)
        }

        return Result.success()
    }
}
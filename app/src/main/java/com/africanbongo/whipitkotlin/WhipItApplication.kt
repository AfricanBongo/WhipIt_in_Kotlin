package com.africanbongo.whipitkotlin

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.africanbongo.whipitkotlin.storage.repository.RecipeWorker
import zw.co.bitpirates.spoonacularclient.service.SpoonacularApi
import timber.log.Timber

class WhipItApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // Set the api key of the spoonacular client.
        SpoonacularApi.setKey("*******************************")

        // Prefetch recipes from API.
        val preloadRecipes: WorkRequest = OneTimeWorkRequestBuilder<RecipeWorker>().build()

        WorkManager
            .getInstance(applicationContext)
            .enqueue(preloadRecipes)
    }
}
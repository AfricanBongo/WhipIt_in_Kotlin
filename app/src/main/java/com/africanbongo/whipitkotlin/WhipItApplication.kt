package com.africanbongo.whipitkotlin

import android.app.Application
import zw.co.bitpirates.spoonacularclient.service.SpoonacularApi
import timber.log.Timber

class WhipItApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // Set the api key of the spoonacular client.
        SpoonacularApi.setKey("ab97a6990e0a441aa7969e73c3c40e3b")
    }
}
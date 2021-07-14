package com.africanbongo.whipitkotlin

import android.app.Application
import zw.co.bitpirates.spoonacularclient.service.SpoonacularApi
import timber.log.Timber

class WhipItApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // Set the api key of the spoonacular client.
        SpoonacularApi.setKey("ddfab49f8dd0483ba21ccf2944815631")
    }
}
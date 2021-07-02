package com.africanbongo.whipitkotlin

import android.app.Application
import timber.log.Timber

class WhipItApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
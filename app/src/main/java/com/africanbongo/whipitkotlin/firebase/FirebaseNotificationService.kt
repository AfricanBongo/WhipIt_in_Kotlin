package com.africanbongo.whipitkotlin.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import timber.log.Timber

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.i("Token created: $token")
    }
}
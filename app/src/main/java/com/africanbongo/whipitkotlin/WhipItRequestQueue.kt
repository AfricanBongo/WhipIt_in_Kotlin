package com.africanbongo.whipitkotlin

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * A singleton class that is used to make requests for data from a remote server.
 */
class WhipItRequestQueue(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: WhipItRequestQueue? = null
        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: WhipItRequestQueue(context).also {
                        INSTANCE = it
                    }
                }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }


    fun <T> addRequest(request: Request<T>) = requestQueue.add(request)
}
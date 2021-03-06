package com.africanbongo.whipitkotlin.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.util.CoilUtils
import com.africanbongo.whipitkotlin.R
import com.africanbongo.whipitkotlin.domain.SummarisedRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

object Notifications {

    private val NOTIFICATION_ID = 1

    /**
     * Creates a notification channel.
     * @param channelId The unique id of the channel.
     * @param channelName The name used to identify the channel.
     * @param context Used to access some application resources.
     */
    fun createChannel(channelId: String, channelName: String, context: Context) {
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

        channel.apply {
            enableLights(true)
            lightColor = context.getColor(R.color.primaryColor)
            enableVibration(true)
            description = "Recipe of the day"
        }

        context.getSystemService(
            NotificationManager::class.java
        ).apply {
            createNotificationChannel(channel)
        }
    }


    suspend fun NotificationManager.dispatchNotification(applicationContext: Context, recipe: SummarisedRecipe) {

        val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.notification_channel_id)
        ).apply {
            setSmallIcon(R.drawable.ic_pizza)
            setContentTitle(applicationContext.getString(R.string.notification_title))
            // Merge the string template with the recipe data.
            val resultString = String.format(
                applicationContext.getString(R.string.notification_text_template),
                recipe.title
            )

            setContentText(resultString)

            // Only use the big picture style if the recipe has an image associated with it.
            recipe.imageUrl?.let {
                val bigPictureStyle = NotificationCompat.BigPictureStyle()
                val imageRequest = ImageRequest.Builder(applicationContext).data(it).build()

                withContext(Dispatchers.Default) {
                    val bitmapImage = ImageLoader
                        .invoke(applicationContext)
                        .execute(imageRequest)
                        .drawable
                        ?.toBitmap()

                    bigPictureStyle.bigPicture(bitmapImage)
                }

                setStyle(bigPictureStyle)
            }

        }

        notify(NOTIFICATION_ID, builder.build())
    }
}
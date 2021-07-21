package com.africanbongo.whipitkotlin.ui.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.africanbongo.whipitkotlin.R
import com.africanbongo.whipitkotlin.domain.SummarisedRecipe
import com.africanbongo.whipitkotlin.ui.list.RecipeListAdapter
import zw.co.bitpirates.spoonacularclient.model.Recipe

/**
 * Fetches an image using a URL string and loads it into the [ImageView].
 */
fun ImageView.fetchImage(imgUrl: String?) {

    if (imgUrl != null) {
        val imgUri = if (imgUrl.contains("https"))
            imgUrl.toUri().buildUpon().scheme("https").build()
        else imgUrl.toUri()

        load(imgUri) {
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_image_not_found)
            crossfade(1000)
            scale(Scale.FILL)
        }

        return
    }

    scaleType = ImageView.ScaleType.CENTER
    load(R.drawable.ic_image_not_found)
}

/**
 * Call to update the loading status using the ImageView.
 * The [viewToShow] will be shown when [status] is of type [FetchResult.Success].
 * @param status [FetchResult] holding state of the request.
 * @param viewToShow To manipulate at different request types.
 */
fun <T: Any> FrameLayout.bindStatusWithView(status: FetchResult<T>, viewToShow: View) {
    val imageView = findViewById<ImageView>(R.id.status_image_view)
    when (status) {
        is FetchResult.Loading -> {
            crossFadeViews(this, viewToShow, 100)
            imageView.setImageResource(R.drawable.ic_loading_pizza)
        }
        is FetchResult.Error -> {
            crossFadeViews(this, viewToShow)
            imageView.setImageResource(R.drawable.ic_error)
        }
        is FetchResult.Success -> {
            crossFadeViews(viewToShow, this)
        }
    }
}

/**
 * Apply a cross-fade animation between the two views.
 * @param revealThisView The view to show when the animation ends.
 * @param hideThisView The view to hide when the animation ends.
 * @param durationInMillis The duration of the animation in milliseconds,
 * e.g. if durationInMillis = 500, hiding animation duration is 500ms and revealing animation is 500ms.
 */
fun crossFadeViews(revealThisView: View, hideThisView: View, durationInMillis: Int = 1000) {

    // Start animation to show this view gradually.
    revealThisView.apply {
        alpha = 0f
        visibility = View.VISIBLE

        animate()
            .alpha(1f)
            .setDuration(durationInMillis.toLong())
            .setListener(null)
    }

    // Start animation to hide this view gradually.
    hideThisView.animate()
        .alpha(0f)
        .setDuration(durationInMillis.toLong())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                hideThisView.visibility = View.GONE
            }
        })
}
/**
 * Binds a list of [Recipe] with a recyclerview.
 * Creates a new adapter if one hadn't been set already.
 * @param listOfItems List of [Recipe] to be displayed.
 */
fun RecyclerView.bindWithData(listOfItems: List<SummarisedRecipe>?) {
    if (adapter == null) adapter = RecipeListAdapter()
    (adapter as RecipeListAdapter).submitList(listOfItems)
}
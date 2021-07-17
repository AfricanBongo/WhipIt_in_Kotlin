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
import coil.transition.CrossfadeTransition
import com.africanbongo.whipitkotlin.R
import com.africanbongo.whipitkotlin.domain.SummarisedRecipe
import com.africanbongo.whipitkotlin.ui.list.RecipeListAdapter
import zw.co.bitpirates.spoonacularclient.model.Recipe

/**
 * Fetches an image using a URL string and loads it into the [ImageView].
 */
fun ImageView.fetchImage(imgUrl: String) {

    val imgUri = if (imgUrl.contains("https"))
        imgUrl.toUri().buildUpon().scheme("https").build()
    else imgUrl.toUri()

    load(imgUri) {
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_image_not_found)
        crossfade(1000)
        scale(Scale.FILL)
    }
}

/**
 * Call to update the loading status using the ImageView.
 * Will set [recyclerView] to be blank when [status] is [FetchResult.Loading] or [FetchResult.Error].
 * @param status [FetchResult] holding state of the request.
 * @param recyclerView [RecyclerView] to manipulate at different requests
 */
fun FrameLayout.bindStatusWithRecyclerView(status: FetchResult<Any>, recyclerView: RecyclerView) {
    when (status) {
        is FetchResult.Loading -> {
            crossFadeViews(this, recyclerView, 100)
            findViewById<ImageView>(R.id.status_image_view).setImageResource(R.drawable.ic_loading_pizza)
        }
        is FetchResult.Error -> {
            crossFadeViews(this, recyclerView)
            findViewById<ImageView>(R.id.status_image_view).setImageResource(R.drawable.ic_error)
        }
        is FetchResult.Success -> {
            crossFadeViews(recyclerView, this)
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
fun RecyclerView.bindWithData(listOfItems: List<SummarisedRecipe>) {
    if (adapter == null) adapter = RecipeListAdapter()
    (adapter as RecipeListAdapter).submitList(listOfItems)
}
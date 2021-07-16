package com.africanbongo.whipitkotlin.ui.util

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import zw.co.bitpirates.spoonacularclient.model.Recipe
import com.africanbongo.whipitkotlin.R
import com.africanbongo.whipitkotlin.domain.SummarisedRecipe
import com.africanbongo.whipitkotlin.ui.list.RecipeListAdapter

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
        crossfade(true)
        scale(Scale.FILL)
    }
}

/**
 * Call to update the loading status using the ImageView.
 * Will set [recyclerView] to be blank when [status] is [FetchResult.Loading] or [FetchResult.Error].
 * @param status [FetchResult] holding state of the request.
 * @param recyclerView [RecyclerView] to manipulate at different requests
 */
fun ImageView.bindStatusWithRecyclerView(status: FetchResult<Any>, recyclerView: RecyclerView) {
    when (status) {
        is FetchResult.Loading -> {
            recyclerView.visibility = View.GONE
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_loading_pizza)
        }
        is FetchResult.Error -> {
            recyclerView.visibility = View.GONE
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_error)
        }
        is FetchResult.Success -> {
            recyclerView.visibility = View.VISIBLE
            visibility = View.GONE
        }
    }
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
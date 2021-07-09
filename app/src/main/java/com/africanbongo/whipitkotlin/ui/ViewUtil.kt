package com.africanbongo.whipitkotlin.ui

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.africanbongo.spoonacularandroid.model.Recipe
import com.africanbongo.whipitkotlin.R
import com.africanbongo.whipitkotlin.network.service.RequestStatus
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
    }
}

/**
 * Call to update the loading status using the ImageView.
 * Will set [recyclerView] to be blank when [status] is [RequestStatus.LOADING] or [RequestStatus.ERROR].
 * @param status [RequestStatus] holding state of the request.
 * @param recyclerView [RecyclerView] to manipulate at different requests
 */
fun ImageView.bindStatusWithRecyclerView(status: RequestStatus, recyclerView: RecyclerView) {
    when (status) {
        RequestStatus.LOADING -> {
            recyclerView.visibility = View.GONE
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_loading_pizza)
        }
        RequestStatus.ERROR -> {
            recyclerView.visibility = View.GONE
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_error)
        }
        RequestStatus.DONE -> {
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
fun RecyclerView.bindWithData(listOfItems: List<Recipe>) {
    if (adapter == null) adapter = RecipeListAdapter()
    (adapter as RecipeListAdapter).submitList(listOfItems)
}
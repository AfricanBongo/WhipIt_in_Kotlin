package com.africanbongo.whipitkotlin.ui

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.africanbongo.whipitkotlin.R
import com.africanbongo.whipitkotlin.model.Recipe
import com.africanbongo.whipitkotlin.network.RequestStatus
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

// TODO Document this.
fun ImageView.bindStatus(status: RequestStatus) {
    when (status) {
        RequestStatus.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_loading_pizza)
        }
        RequestStatus.ERROR -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_error)
        }
        RequestStatus.DONE -> visibility = View.GONE
    }
}
// TODO Document this.
fun RecyclerView.bindWithData(listOfItems: List<Recipe>) {
    if (adapter == null) adapter = RecipeListAdapter()
    (adapter as RecipeListAdapter).submitList(listOfItems)
}
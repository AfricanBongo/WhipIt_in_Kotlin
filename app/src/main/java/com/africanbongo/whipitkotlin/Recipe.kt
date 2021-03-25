package com.africanbongo.whipitkotlin

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder

/**
 * Is used to hold basic information about a recipe
 */
data class Recipe(
    var title: String,
    var sourceName: String,
    var imageURL: String?,
    var servings: String,
    var readyInMinutes: String)

object ImageLoader{
    /**
     * Loads an image into [imageView] using the provided [imageURL],
     * if the recipe doesn't have image it adds a placeholder image
     */
    fun loadRecipeImage(imageView: ImageView, imageURL: String?) =
            imageURL?.apply {
                Glide
                        .with(imageView)
                        .load(imageURL)
                        .into(imageView)
            }

}


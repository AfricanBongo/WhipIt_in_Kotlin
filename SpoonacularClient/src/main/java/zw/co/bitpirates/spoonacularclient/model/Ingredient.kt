package zw.co.bitpirates.spoonacularclient.model

import com.squareup.moshi.JsonClass

/**
 * An ingredient used in a recipe.
 */
@JsonClass(generateAdapter = true)
data class Ingredient (
    val id: Int,
    val amount: Double,
    val unit: String?,
    val image: String?,
    val name: String,
    val nameClean: String,
    val original: String,
    val originalString: String,
    /**
     * The description of the ingredient without any amount or unit attached to it.
     * E.g. "clove(s) of garlic"
     */
    val originalName: String
)
package zw.co.bitpirates.spoonacularclient.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Network object that represents a step of a recipe.
 */
@JsonClass(generateAdapter = true)
data class Instruction (
    /**
     * The step number in the order of other steps.
     */
    val number: Int,

    /**
     * The instruction within the step.
     */
    val step: String,
)

/**
 * Container class for the list of [Instruction]s used in a recipe.
 */
data class Steps (
    @Json(name = "steps") val listOfInstructions: List<Instruction>
)
package zw.co.bitpirates.spoonacularclient.model

/**
 * Maps a list of strings into a list of [CuisineEnum]s.
 * @see CuisineEnum
 */
fun List<String>?.toCuisineEnums(): List<CuisineEnum>? = this?.map {
    CuisineEnum.valueOf(
        it.uppercase()
    )
}

/**
 * Transform a list of [CuisineEnum] to a list of Strings.
 */
fun List<CuisineEnum>.asStrings(): List<String> =
    map { cuisine ->
        cuisine.type.replaceFirstChar { it.titlecase() }
    }
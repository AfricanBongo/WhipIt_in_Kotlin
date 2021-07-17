package zw.co.bitpirates.spoonacularclient.model

/**
 * Maps a list of strings returned into [Recipe] object's cuisine property into a list of [CuisineEnum]s.
 *
 * Due to the Spoonacular API not documenting some of their cuisines
 * we have to chosen to map most undocumented cuisines as [CuisineEnum.NONE].
 * Please take notice of this when using this extension function for yourself.
 * @return A list of mapped [CuisineEnum]s.
 * @see CuisineEnum
 */
fun List<String>?.toCuisineEnums(): List<CuisineEnum>? = this?.map {
     try {
         CuisineEnum.valueOf(
             it.replace(" ", "_").uppercase()
         )
     } catch (e: IllegalArgumentException) {
         CuisineEnum.NONE
     }
}

/**
 * Map a list of [CuisineEnum] to a list of titled Strings.
 *
 * E.g. ```["American", "Eastern european", "African"]```
 */
fun List<CuisineEnum>.asStrings(): List<String> =
    map { cuisine ->
        cuisine.type.replace("_", " ").replaceFirstChar { it.titlecase() }
    }
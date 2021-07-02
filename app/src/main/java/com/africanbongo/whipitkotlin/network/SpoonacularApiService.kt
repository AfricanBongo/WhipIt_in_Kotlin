package com.africanbongo.whipitkotlin.network

import com.africanbongo.whipitkotlin.model.Recipe
import com.africanbongo.whipitkotlin.model.RecipeCuisine
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber
import java.util.logging.Logger

/**
 * URL for fetching data from the [Spoonacular API](https://spoonacular.com/food-api).
 */
private const val BASE_URL = "https://api.spoonacular.com/"

/**
 * The API Key used to access information from the [Spoonacular API](https://spoonacular.com/food-api).
 */
private const val API_KEY = "ddfab49f8dd0483ba21ccf2944815631"

/**
 * The expected number of recipes to be returned from a query.
 */
enum class QueryNumber(val value: Int) {
    SMALL(10), MEDIUM(25), LARGE(50), CRAZY(100)
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val interceptor = HttpLoggingInterceptor {
    Timber.tag("OkHttp").i(it)
}.apply {
    level = HttpLoggingInterceptor.Level.BODY
}

// TODO Doc
private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


/**
 * Exposes the [SpoonacularApiService] to be used to make API calls.
 */
object SpoonacularApi {
    /**
     * Use to access data from the Spoonacular API.
     */
    val retrofitService: SpoonacularApiService by lazy { retrofit.create(SpoonacularApiService::class.java)}
}


// TODO Check request error again
/**
 * Retrofit API Service that fetches data from the [Spoonacular API](https://spoonacular.com/food-api) database.
 */
interface SpoonacularApiService {
    /**
     * Asynchronous call to fetch a list of random recipes from the API.
     * @param cuisine The type of cuisine that the recipes should belong to,
     * this is by default set to not return recipes of any specific cuisine.
     * Make sure to pass value of a [RecipeCuisine] enum as a cuisine type if need arises.
     * @param numberOfRecipes [QueryNumber] enum representing the number of recipes to be returned in the request.
     * Set to [QueryNumber.MEDIUM]'s value by default.
     * @param apiKey Only change this when you are using a different API Key,
     * as a default has already been set.
     * @return A list of [Recipe]
     */
    @GET(value = "/recipes/random")
    suspend fun getRandomRecipesOfType(@Query("apiKey") apiKey: String = API_KEY,
                                       @Query("tags") cuisine: String? = null,
                                       @Query("number") numberOfRecipes: Int = QueryNumber.MEDIUM.value,
                                       ): RecipeList
}
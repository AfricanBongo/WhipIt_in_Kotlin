package com.africanbongo.spoonacularandroid.service

import com.africanbongo.spoonacularandroid.model.Recipe
import com.africanbongo.spoonacularandroid.model.RecipeList
import com.africanbongo.spoonacularandroid.service.SpoonacularApi.apiKey
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * URL for fetching data from the [Spoonacular API](https://spoonacular.com/food-api).
 */
private const val BASE_URL = "https://api.spoonacular.com/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Client to access data from the API.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * Exposes the [SpoonacularApiService] to be used to make API calls.
 *
 * Make sure to set the [apiKey] within this object in the Application class before starting to use Api.
 * Otherwise the requests will not go through.
 */
object SpoonacularApi {

    /**
     * Key used to access information in the Spoonacular online database.
     * Please only set this in your Application class and best not to change it elsewhere.
     */
    var apiKey: String?= null

    /**
     * Use to access data from the Spoonacular API.
     */
    val retrofitService: SpoonacularApiService by lazy { retrofit.create(SpoonacularApiService::class.java)}
}

/**
 * Retrofit API Service that fetches data from the [Spoonacular API](https://spoonacular.com/food-api) database.
 */
interface SpoonacularApiService {
    /**
     * Asynchronous call to fetch a list of random recipes from the API.
     * @param cuisine The type of cuisine that the recipes should belong to,
     * this is by default set to not return recipes of any specific cuisine.
     * Make sure to pass value of a [Cuisine] enum as a cuisine type if need arises.
     * @param numberOfRecipes The number of recipes to be returned in the request.
     * It's most preferable to use enum [QueryNumber]s values in this place
     * Set to [QueryNumber.MEDIUM]'s value by default.
     * @param apiKey Only change this when you are using a different API Key,
     * as a default has already been set.
     * @return A list of [Recipe]
     */
    @GET(value = "/recipes/random")
    suspend fun getRandomRecipesOfType(@Query("apiKey") apiKey: String = SpoonacularApi.apiKey!!,
                                       @Query("tags") cuisine: String? = null,
                                       @Query("number") numberOfRecipes: Int = QueryNumber.MEDIUM.value,
    ): RecipeList
}
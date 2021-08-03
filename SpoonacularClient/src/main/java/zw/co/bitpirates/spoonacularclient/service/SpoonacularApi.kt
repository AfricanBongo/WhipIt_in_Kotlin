package zw.co.bitpirates.spoonacularclient.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import zw.co.bitpirates.spoonacularclient.exception.ServerException
import zw.co.bitpirates.spoonacularclient.model.CuisineEnum
import zw.co.bitpirates.spoonacularclient.model.Recipe
import zw.co.bitpirates.spoonacularclient.model.RecipeList
import zw.co.bitpirates.spoonacularclient.service.SpoonacularApi.getRecipesOfCuisine
import zw.co.bitpirates.spoonacularclient.service.SpoonacularApi.setKey

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
 * Make sure to use [setKey] method within this object in the Application class before starting to use Api.
 * Otherwise the requests will not go through.
 */
object SpoonacularApi {

    /*
    Key used to access information in the Spoonacular online database.
     */
    private var apiKey: String?= null

    /**
     * Set the key to be used to access the Spoonacular online API.
     * Please only set this in your Application class and best not to change it elsewhere.
     */
    fun setKey(apiKey: String) {
        this.apiKey = apiKey
    }

    /**
     * Use to access data from the Spoonacular API.
     */
    private val retrofitService: SpoonacularApiService by lazy { retrofit.create(SpoonacularApiService::class.java)}

    /**
     * Retrieve recipes of a type of [cuisine] using the [CuisineEnum],
     * of a certain size, specified by using [QueryNumber] in the [numberOfRecipes] argument.
     * @param cuisine [CuisineEnum] used to fetch recipe.
     * @param numberOfRecipes [QueryNumber] specifying the number of recipes to fetch for the request.
     * @throws ServerException If there's an error with communicating with Spoonacular API.
     */
    @Throws(ServerException::class)
    suspend fun getRecipesOfCuisine(
        cuisine: CuisineEnum,
        numberOfRecipes: QueryNumber = QueryNumber.LARGE
    ): List<Recipe> = getRecipesOfCuisine(cuisine, numberOfRecipes.value)


    /**
     * Retrieve recipes of a type of [CuisineEnum], of a certain size [numberOfRecipes].
     * Using the [getRecipesOfCuisine] that has [numberOfRecipes] argument of type [QueryNumber] is most preferable, to avoid getting errors.
     * @param cuisine A [CuisineEnum] that specifies the type of cuisine the recipes will belong to.
     * @param numberOfRecipes The number here should be in the range of 1 to 100, else an exception is thrown.
     * @throws IllegalArgumentException If [numberOfRecipes] is not within the range (1 to 100) specified by the argument.
     */
    @Throws(IllegalAccessException::class, ServerException::class)
    suspend fun getRecipesOfCuisine(cuisine: CuisineEnum, numberOfRecipes: Int): List<Recipe> {
        if (numberOfRecipes in 1..100) {
            // If the cuisine enum identifies as CuisineEnum.NONE, pass parameter to base method as null.
            val cuisineString: String? = if (cuisine == CuisineEnum.NONE) null else cuisine.type

            return getRecipesOfCuisine(cuisineString, numberOfRecipes)?.listOfRecipes
                ?: throw ServerException(
                    message = "Couldn't fetch the list of recipes successfully",
                    Exception()
                )
        }

        throw IllegalArgumentException("numberOfRecipes argument should be in the range 1 to 100.")
    }


    private suspend fun getRecipesOfCuisine(cuisine: String?, numberOfRecipes: Int) = withContext(Dispatchers.IO) {
        apiKey?.let {
            retrofitService.getRandomRecipesOfType(apiKey = it, cuisine = cuisine, numberOfRecipes = numberOfRecipes)
        }

    }
}

/**
 * Retrofit API Service that fetches data from the [Spoonacular API](https://spoonacular.com/food-api) database.
 */
internal interface SpoonacularApiService {
    /**
     * Asynchronous call to fetch a list of random recipes from the API.
     * @param cuisine The type of cuisine that the recipes should belong to,
     * this is by default set to not return recipes of any specific cuisine.
     * @param numberOfRecipes The number of recipes to be returned in the request.
     * @param apiKey API Key used for authentication process in fetching data.
     * @return A list of [Recipe] wrapped in a [RecipeList] object.
     */
    @GET(value = "/recipes/random")
    suspend fun getRandomRecipesOfType(@Query("apiKey") apiKey: String,
                                       @Query("tags") cuisine: String? = null,
                                       @Query("number") numberOfRecipes: Int,
    ): RecipeList
}
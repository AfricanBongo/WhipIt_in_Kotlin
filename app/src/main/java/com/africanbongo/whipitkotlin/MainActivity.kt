package com.africanbongo.whipitkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.africanbongo.whipitkotlin.databinding.ActivityMainBinding
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recipeRecyclerview.adapter = RecipeRecyclerViewAdapter(emptyList())
        fetchData()
    }

    /**
     * Fetch data from the server and load into a mutable list.
     * Uses [Request] to request for data from the Spoonacular API.
     * Passes an immutable list of [Recipe] to the [loadRecipes] function.
     */
    fun fetchData() {
        val title = "title"
        val source = "sourceName"
        val servings = "servings"
        val readyInMinutes = "readyInMinutes"
        val image = "image"
        val imageNotFound = "https://bitsofco.de/content/images/2018/12/broken-1.png"

        val fetchedRecipes = mutableListOf<Recipe>()
        val recipesListener: Response.Listener<JSONObject> = Response.Listener {
                jsonResponse ->

            // Deserialize JSON
            val recipesArray = jsonResponse.getJSONArray("recipes")


            for (i in 0 until recipesArray.length()) {
                val recipeJSONObject = recipesArray.getJSONObject(i)

                val recipeTitle = recipeJSONObject.getString(title)
                val recipeSourceName = "by ${recipeJSONObject.getString(source)}"
                val recipeImageURL = recipeJSONObject.optString(image, imageNotFound)
                val recipeServings = recipeJSONObject.getInt(servings).toString()
                val recipePreparationTime = "${recipeJSONObject.getInt(readyInMinutes)} min"

                fetchedRecipes.add(
                    Recipe(recipeTitle, recipeSourceName, recipeImageURL, recipeServings, recipePreparationTime)
                )
            }

            // When the deserialization of the JSON has finished, load the recipes into the app
            loadRecipes(fetchedRecipes)
        }

        // Log error, if there's an error in fetching the data
        val errorListener: Response.ErrorListener = Response.ErrorListener {
                volleyError -> Log.e("Fetching JSON Recipes", volleyError.message, volleyError)
        }

        val fetchDataURL = "https://api.spoonacular.com/recipes/random?" +
                "apiKey=ddfab49f8dd0483ba21ccf2944815631" +
                "&number=50"

        val recipeListRequest = JsonObjectRequest(
            Request.Method.GET, fetchDataURL, null, recipesListener, errorListener
        )

        WhipItRequestQueue.getInstance(this).addRequest(recipeListRequest)
    }

    /**
     * Load new [RecipeRecyclerViewAdapter] with the [recipeList]
     * Then invalidate the recycler view to refresh the data within it.
     */
    fun loadRecipes(recipeList: List<Recipe>) {
        binding.recipeRecyclerview.adapter = RecipeRecyclerViewAdapter(recipeList)
        binding.invalidateAll()
    }
}


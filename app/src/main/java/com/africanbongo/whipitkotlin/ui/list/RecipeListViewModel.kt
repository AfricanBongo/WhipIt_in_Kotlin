package com.africanbongo.whipitkotlin.ui.list

import android.app.Application
import androidx.lifecycle.*
import com.africanbongo.whipitkotlin.model.Recipe
import com.africanbongo.whipitkotlin.model.RecipeCuisine
import com.africanbongo.whipitkotlin.network.QueryNumber
import com.africanbongo.whipitkotlin.network.RequestStatus
import com.africanbongo.whipitkotlin.network.SpoonacularApi
import kotlinx.coroutines.launch
import timber.log.Timber

class RecipeListViewModel : ViewModel() {

    private var _status = MutableLiveData<RequestStatus>()
    private var _listOfRecipes = MutableLiveData<List<Recipe>>()

    /**
     * The status of the request for the list of recipes.
     */
    val status: LiveData<RequestStatus> = _status

    /**
     * The list of recipes fetched from storage.
     */
    val listOfRecipes: LiveData<List<Recipe>> = _listOfRecipes

    // TODO Document this
    fun fetchRecipes(cuisine: RecipeCuisine? = null, numberOfRecipes: QueryNumber = QueryNumber.MEDIUM) {
        viewModelScope.launch {
            _status.value = RequestStatus.LOADING

            // Try to load list of recipes, and update request status accordingly.
            try {
                _listOfRecipes.value = SpoonacularApi.retrofitService.getRandomRecipesOfType(
                    cuisine = cuisine?.type,
                    numberOfRecipes = numberOfRecipes.value
                ).listOfRecipes
                _status.value = RequestStatus.DONE
            } catch (e: Exception) {
                Timber.e(e)
                _status.value = RequestStatus.ERROR
                _listOfRecipes.value = ArrayList()
            }
        }
    }
}
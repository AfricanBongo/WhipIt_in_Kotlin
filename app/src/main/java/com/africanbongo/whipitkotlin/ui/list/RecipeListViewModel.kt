package com.africanbongo.whipitkotlin.ui.list

import androidx.lifecycle.*
import com.africanbongo.whipitkotlin.network.model.Recipe
import com.africanbongo.whipitkotlin.network.model.RecipeCuisine
import com.africanbongo.whipitkotlin.network.service.QueryNumber
import com.africanbongo.whipitkotlin.network.service.RequestStatus
import com.africanbongo.whipitkotlin.network.service.SpoonacularApi
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

    /**
     * Fetches the list of [Recipes] from the API and updates [listOfRecipes]'s value.
     * @param cuisine The cuisine that the list of recipes should belong to,
     * set to null if recipes from random and different cuisines are acceptable.
     * It is set to null by default though.
     * @param numberOfRecipes Uses enum [QueryNumber] to determine that the number of recipes that should be returned.
     */
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
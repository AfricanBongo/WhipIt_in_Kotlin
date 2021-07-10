package com.africanbongo.whipitkotlin.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.africanbongo.spoonacularandroid.model.Recipe
import com.africanbongo.spoonacularandroid.service.QueryNumber
import com.africanbongo.spoonacularandroid.model.CuisineEnum
import com.africanbongo.spoonacularandroid.service.SpoonacularApi
import com.africanbongo.whipitkotlin.ui.RequestStatus
import kotlinx.coroutines.launch
import timber.log.Timber

class RecipeListViewModel : ViewModel() {

    private val _queryNumberList = QueryNumber.values().asList()
    private val _cuisineTypes = CuisineEnum.values().asList()
    private var _status = MutableLiveData<RequestStatus>()
    private var _listOfRecipes = MutableLiveData<List<Recipe>>()

    private var currentNumber: QueryNumber = QueryNumber.MEDIUM
    private var currentCuisineEnum: CuisineEnum = CuisineEnum.ALL


    init {
        fetchRecipes()
    }

    /**
     * The status of the request for the list of recipes.
     */
    val status: LiveData<RequestStatus> = _status

    /**
     * A list of the [CuisineEnum] enums.
     */
    val cuisineTypes = _cuisineTypes.asStrings()

    /**
     * A list of the [QueryNumber] enums.
     */
    val queryNumberList = _queryNumberList.map { it.value.toString() }

    /**
     * The list of recipes fetched from storage.
     */
    val listOfRecipes: LiveData<List<Recipe>> = _listOfRecipes

    /**
     * Fetches the list of [Recipe] from the API and updates [listOfRecipes]'s value.
     */
    private fun fetchRecipes() {
        viewModelScope.launch {
            _status.value = RequestStatus.LOADING

            // Try to load list of recipes, and update request status accordingly.
            try {
                _listOfRecipes.value = SpoonacularApi.retrofitService.getRandomRecipesOfType(
                    cuisine = if (currentCuisineEnum == CuisineEnum.ALL) null else currentCuisineEnum.type,
                    numberOfRecipes = currentNumber.value
                ).listOfRecipes
                _status.value = RequestStatus.DONE
            } catch (e: Exception) {
                Timber.e(e)
                _status.value = RequestStatus.ERROR
                _listOfRecipes.value = ArrayList()
            }
        }
    }

    /**
     * Change the [QueryNumber] that should be used in fetching the list of recipes
     * @param listPosition The position of the number in the [queryNumberList].
     */
    fun changeNumber(listPosition: Int) {
        currentNumber = _queryNumberList[listPosition]
        fetchRecipes()
    }

    /**
     * Change the [CuisineEnum] that should be used in fetching the list of recipes
     * @param listPosition The position of the number in the [cuisineTypes].
     */
    fun changeCuisine(listPosition: Int) {
        currentCuisineEnum = _cuisineTypes[listPosition]
        fetchRecipes()
    }

    private fun List<CuisineEnum>.asStrings(): List<String> =
        map { cuisine ->
            cuisine.type.replaceFirstChar { it.titlecase() }
        }
}
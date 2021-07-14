package com.africanbongo.whipitkotlin.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.africanbongo.whipitkotlin.domain.DomainRecipe
import com.africanbongo.whipitkotlin.storage.database.RecipeRepository
import com.africanbongo.whipitkotlin.ui.FetchResult
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import zw.co.bitpirates.spoonacularclient.model.CuisineEnum
import zw.co.bitpirates.spoonacularclient.model.Recipe
import zw.co.bitpirates.spoonacularclient.service.QueryNumber

class RecipeListViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _queryNumberList = QueryNumber.values().asList()
    private val _cuisineTypes = CuisineEnum.values().asList()

    private var currentNumber: QueryNumber = QueryNumber.MEDIUM
    private var currentCuisineEnum: CuisineEnum = CuisineEnum.AMERICAN


    init {
        fetchRecipes()
    }

    /**
     * A list of the [CuisineEnum] enums.
     */
    val cuisineTypes = _cuisineTypes.asStrings()

    /**
     * A list of the [QueryNumber] enums.
     */
    val queryNumberList = _queryNumberList.map { it.value.toString() }

    /**
     * Request for the list of recipes fetched from the repository wrapped in a state-flow.
     */
    val recipeResult: StateFlow<FetchResult<List<DomainRecipe>>> = repository.recipeResult

    /**
     * Fetches the list of [Recipe] from the API and updates [recipeResult]'s value.
     */
    private fun fetchRecipes() {
        viewModelScope.launch {
            repository.refreshCacheFor(currentCuisineEnum)
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
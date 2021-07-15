package com.africanbongo.whipitkotlin.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.africanbongo.whipitkotlin.domain.DomainRecipe
import com.africanbongo.whipitkotlin.domain.toDomainModel
import com.africanbongo.whipitkotlin.storage.database.model.toDatabaseModel
import com.africanbongo.whipitkotlin.storage.repository.RecipeRepository
import com.africanbongo.whipitkotlin.ui.FetchResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import zw.co.bitpirates.spoonacularclient.model.CuisineEnum
import zw.co.bitpirates.spoonacularclient.model.Recipe
import zw.co.bitpirates.spoonacularclient.service.QueryNumber

class RecipeListViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _queryNumberList = QueryNumber.values().asList()
    private val _cuisineTypes = CuisineEnum.values().asList()

    private var currentNumber: QueryNumber = QueryNumber.MEDIUM
    private var currentCuisineEnum: CuisineEnum = CuisineEnum.AMERICAN

    /**
     * A list of the [CuisineEnum] enums.
     */
    val cuisineTypes = _cuisineTypes.asStrings()

    /**
     * A list of the [QueryNumber] enums.
     */
    val queryNumberList = _queryNumberList.map { it.value.toString() }

    private val _recipeResult = MutableStateFlow<FetchResult<List<DomainRecipe>>>(FetchResult.Loading)

    /**
     * Request for the list of recipes fetched from the repository wrapped in a state-flow.
     */
    val recipeResult: StateFlow<FetchResult<List<DomainRecipe>>> = _recipeResult

    init {

        viewModelScope.launch {
            repository.refreshCacheFor()

            repository.recipeList.collect {
                _recipeResult.value = FetchResult.success(it)
            }
        }
    }

    /**
     * Change the [QueryNumber] that should be used in fetching the list of recipes
     * @param listPosition The position of the number in the [queryNumberList].
     */
    fun changeNumber(listPosition: Int) {
        currentNumber = _queryNumberList[listPosition]
    }

    /**
     * Change the [CuisineEnum] that should be used in fetching the list of recipes
     * @param listPosition The position of the number in the [cuisineTypes].
     */
    fun changeCuisine(listPosition: Int) {
        currentCuisineEnum = _cuisineTypes[listPosition]
    }

    private fun List<CuisineEnum>.asStrings(): List<String> =
        map { cuisine ->
            cuisine.type.replaceFirstChar { it.titlecase() }
        }
}
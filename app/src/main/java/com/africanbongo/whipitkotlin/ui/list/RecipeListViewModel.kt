package com.africanbongo.whipitkotlin.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.africanbongo.whipitkotlin.domain.SummarisedRecipe
import com.africanbongo.whipitkotlin.storage.repository.RecipeRepository
import com.africanbongo.whipitkotlin.ui.util.FetchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import zw.co.bitpirates.spoonacularclient.exception.ServerException
import zw.co.bitpirates.spoonacularclient.model.CuisineEnum
import zw.co.bitpirates.spoonacularclient.model.asStrings
import zw.co.bitpirates.spoonacularclient.service.QueryNumber

class RecipeListViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _cuisineTypes = CuisineEnum.values().asList()
    private val _recipeResult = MutableStateFlow<FetchResult<List<SummarisedRecipe>>>(FetchResult.Loading)

    /**
     * A list of the [CuisineEnum] enums.
     */
    val cuisineTypes = _cuisineTypes.asStrings().toMutableList().apply {
        if (this[0] == "None") this[0] = "Random"
    }.toList()


    /**
     * Request for the list of recipes fetched from the repository wrapped in a state-flow.
     */
    val recipeResult: StateFlow<FetchResult<List<SummarisedRecipe>>> = _recipeResult

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.summarisedRecipeList.collect {
                    _recipeResult.value = FetchResult.success(it)
                }
            }
        }
    }

    /**
     * Change the [CuisineEnum] that should be used in fetching the list of recipes.
     * @param listPosition The position of the number in the [cuisineTypes].
     */
    fun changeCuisine(listPosition: Int) {
        _recipeResult.value = FetchResult.Loading
        viewModelScope.launch {
            try {
                repository.refreshCacheFor(_cuisineTypes[listPosition], true)
            } catch (e: ServerException) {
                _recipeResult.value = FetchResult.error(e.message!!)
            }
        }
    }
}
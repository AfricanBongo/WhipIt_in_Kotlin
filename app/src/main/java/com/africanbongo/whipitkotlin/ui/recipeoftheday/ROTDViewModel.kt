package com.africanbongo.whipitkotlin.ui.recipeoftheday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.africanbongo.whipitkotlin.domain.SummarisedRecipe
import com.africanbongo.whipitkotlin.storage.repository.RecipeRepository
import com.africanbongo.whipitkotlin.ui.util.FetchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class ROTDViewModel(private val repository: RecipeRepository): ViewModel() {

    private val _recipeOfTheDay = MutableStateFlow<FetchResult<SummarisedRecipe>>(FetchResult.Loading)

    /**
     * The recipe of the day [FetchResult] wrapped within a [StateFlow].
     */
    val recipeOfTheDay: StateFlow<FetchResult<SummarisedRecipe>> = _recipeOfTheDay

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.recipesOfTheDay.collect {
                    if (it.isNotEmpty() && _recipeOfTheDay.value !is FetchResult.Success) {
                        _recipeOfTheDay.value = FetchResult.success(it.first())
                    }
                }
            }
        }
    }
}

class ROTDViewModelFactory(private val repository: RecipeRepository): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ROTDViewModel::class.java))
            return ROTDViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class.")
    }

}
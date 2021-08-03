package com.africanbongo.whipitkotlin.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.africanbongo.whipitkotlin.R
import com.africanbongo.whipitkotlin.databinding.FragmentListBinding
import com.africanbongo.whipitkotlin.storage.database.RecipeDatabase
import com.africanbongo.whipitkotlin.storage.repository.RecipeRepository
import com.africanbongo.whipitkotlin.ui.util.FetchResult
import com.africanbongo.whipitkotlin.ui.util.bindStatusWithView
import com.africanbongo.whipitkotlin.ui.util.bindWithData
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecipeListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentListBinding.inflate(inflater)

        // Get ViewModel
        val viewModel = createViewModel()
        binding.viewModel = viewModel

        configureChipGroup(binding.cuisineChipGroup, viewModel)

        // Collect the result from the and update recyclerview list.
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipeResult.collect { result ->

                    when (result) {
                        is FetchResult.Success -> binding.recipeRecyclerview.bindWithData(result.data)
                        is FetchResult.Error -> {
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        is FetchResult.Loading -> {}
                    }

                    binding.loadingView.bindStatusWithView(result, binding.recipeRecyclerview)
                }
            }
        }

        binding.lifecycleOwner = this
        return binding.root
    }

    private fun createViewModel(): RecipeListViewModel {
        val recipeDataSource = RecipeDatabase.getInstance(requireContext()).recipeDao
        val recipeRepository = RecipeRepository(recipeDataSource)
        return RecipeListViewModel(recipeRepository)
    }

    /**
     * Add chips into chip-group and bind with the view-model.
     */
    private fun configureChipGroup(chipGroup: ChipGroup, viewModel: RecipeListViewModel) {
        viewModel.cuisineTypes.forEachIndexed { index, cuisine ->
            chipGroup.addView(Chip(requireContext()).apply {
                text = cuisine
                id = index + 1
            })
        }

        // When a chip is checked change the cuisine.
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.changeCuisine(checkedId - 1)
        }

        // Check the first chip by default
        val firstChipId = 1
        chipGroup.check(firstChipId)
    }
}
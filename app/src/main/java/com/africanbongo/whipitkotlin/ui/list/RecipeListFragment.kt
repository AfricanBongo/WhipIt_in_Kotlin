package com.africanbongo.whipitkotlin.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.africanbongo.whipitkotlin.databinding.FragmentListBinding
import com.africanbongo.whipitkotlin.storage.database.RecipeDatabase
import com.africanbongo.whipitkotlin.storage.repository.RecipeRepository
import com.africanbongo.whipitkotlin.ui.util.FetchResult
import com.africanbongo.whipitkotlin.ui.util.bindStatusWithRecyclerView
import com.africanbongo.whipitkotlin.ui.util.bindWithData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import zw.co.bitpirates.spoonacularclient.model.CuisineEnum

class RecipeListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val recipeDataSource = RecipeDatabase.getInstance(requireContext()).recipeDao
        val recipeRepository = RecipeRepository(recipeDataSource, CuisineEnum.FRENCH)

        // Get ViewModel
        val viewModel = RecipeListViewModel(recipeRepository)
        binding.viewModel = viewModel

        // Collect the result from the and update recyclerview list.
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipeResult.collect { result ->

                    binding.statusImageView.bindStatusWithRecyclerView(result, binding.recipeRecyclerview)

                    if (result is FetchResult.Success) {
                        binding.recipeRecyclerview.bindWithData(result.data)
                    }
                }
            }
        }
        return binding.root
    }

}
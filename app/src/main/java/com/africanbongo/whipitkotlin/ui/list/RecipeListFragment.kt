package com.africanbongo.whipitkotlin.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.africanbongo.whipitkotlin.databinding.FragmentListBinding
import com.africanbongo.whipitkotlin.storage.database.RecipeDatabase
import com.africanbongo.whipitkotlin.storage.database.RecipeRepository
import com.africanbongo.whipitkotlin.ui.FetchResult
import com.africanbongo.whipitkotlin.ui.bindStatusWithRecyclerView
import com.africanbongo.whipitkotlin.ui.bindWithData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecipeListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val recipeDataSource = RecipeDatabase.getInstance(requireContext()).recipeDao
        val recipeRepository = RecipeRepository(recipeDataSource)

        // Get ViewModel
        val viewModel = RecipeListViewModel(recipeRepository)
        binding.viewModel = viewModel


        configureSpinners(binding, viewModel)

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

    /*
    Set up the spinners for choosing the cuisine type and number of recipes to return.
     */
    private fun configureSpinners(binding: FragmentListBinding, viewModel: RecipeListViewModel) {

        binding.cuisineSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.changeCuisine(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.numberSpinner.setSelection(1)
        binding.numberSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.changeNumber(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}
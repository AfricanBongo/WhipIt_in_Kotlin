package com.africanbongo.whipitkotlin.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.africanbongo.whipitkotlin.databinding.FragmentListBinding
import com.africanbongo.whipitkotlin.ui.bindStatusWithRecyclerView
import com.africanbongo.whipitkotlin.ui.bindWithData

class RecipeListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        // Get ViewModel
        val viewModel = RecipeListViewModel()
        binding.viewModel = viewModel


        configureSpinners(binding, viewModel)

        // Observe the list of recipes and update recyclerview list.
        viewModel.listOfRecipes.observe(viewLifecycleOwner, {
            binding.recipeRecyclerview.bindWithData(it)
        })

        // Observe request status and update the status ImageView accordingly.
        viewModel.status.observe(viewLifecycleOwner, {
            binding.statusImageView.bindStatusWithRecyclerView(it, binding.recipeRecyclerview)
        })

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
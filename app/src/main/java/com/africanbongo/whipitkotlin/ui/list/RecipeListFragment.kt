package com.africanbongo.whipitkotlin.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.africanbongo.whipitkotlin.databinding.FragmentListBinding
import com.africanbongo.whipitkotlin.ui.bindStatus
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

        // Observe the list of recipes and update recyclerview list.
        viewModel.listOfRecipes.observe(viewLifecycleOwner, {
            binding.recipeRecyclerview.bindWithData(it)
        })

        // Observe request status and update the status ImageView accordingly.
        viewModel.status.observe(viewLifecycleOwner, {
            binding.statusImageView.bindStatus(it)
        })

        viewModel.fetchRecipes()

        return binding.root
    }
}
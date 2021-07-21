package com.africanbongo.whipitkotlin.ui.recipeoftheday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.*
import com.africanbongo.whipitkotlin.R
import com.africanbongo.whipitkotlin.databinding.FragmentRecipeOfTheDayBinding
import com.africanbongo.whipitkotlin.storage.database.RecipeDatabase
import com.africanbongo.whipitkotlin.storage.repository.RecipeRepository
import com.africanbongo.whipitkotlin.ui.util.FetchResult
import com.africanbongo.whipitkotlin.ui.util.bindStatusWithView
import com.africanbongo.whipitkotlin.ui.util.fetchImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeOfTheDayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeOfTheDayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecipeOfTheDayBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModel = createViewModel()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipeOfTheDay.collect { result ->
                    if (result is FetchResult.Success) {
                            binding.recipe = result.data
                            binding.rotdImageView.fetchImage(result.data.imageUrl)
                        }
                    }
                }
            }
        return binding.root
    }


    private fun createViewModel(): ROTDViewModel {
        val recipeDataSource = RecipeDatabase.getInstance(requireContext()).recipeDao
        val repository = RecipeRepository(recipeDataSource)
        val viewModelFactory = ROTDViewModelFactory(repository)
        return ViewModelProvider(this, viewModelFactory).get(ROTDViewModel::class.java)
    }
    companion object {
        @JvmStatic
        fun newInstance() = RecipeOfTheDayFragment()
    }
}
package com.africanbongo.whipitkotlin.ui.recipeoftheday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.africanbongo.whipitkotlin.R

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeOfTheDayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeOfTheDayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_of_the_day, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecipeOfTheDayFragment()
    }
}
package com.africanbongo.whipitkotlin

import android.os.Bundle
import android.transition.Slide
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.africanbongo.whipitkotlin.databinding.ActivityMainBinding
import com.google.android.material.chip.Chip
import zw.co.bitpirates.spoonacularclient.model.CuisineEnum
import zw.co.bitpirates.spoonacularclient.model.asStrings

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val chipGroup = binding.cuisineChipGroup

        createChips().forEach {
            chipGroup.addView(it)
        }

        // TODO Implement the methods to select different recipe lists.
        // TODO Fix chip styling problem
    }

    /**
     * Create and return a list of chips containing the names of cuisines
     */
    private fun createChips(): List<Chip> {
        val cuisines = CuisineEnum.values().asList().asStrings()
        val chips = mutableListOf<Chip>()

        for (cuisine in cuisines) {
            chips.add(Chip(this).apply {
                text = cuisine
            })
        }

        return chips.toList()
    }
}


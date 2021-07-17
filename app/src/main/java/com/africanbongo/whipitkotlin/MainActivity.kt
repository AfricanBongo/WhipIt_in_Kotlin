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
    }

}


package com.africanbongo.whipitkotlin.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.africanbongo.whipitkotlin.databinding.FragmentListBinding

class ListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return binding.root
    }
}
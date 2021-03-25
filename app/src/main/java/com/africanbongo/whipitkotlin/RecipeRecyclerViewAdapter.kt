package com.africanbongo.whipitkotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.africanbongo.whipitkotlin.databinding.RecipeViewholderBinding

class RecipeRecyclerViewAdapter(private val recipeList: List<Recipe>):
    ListAdapter<Recipe, RecipeRecyclerViewAdapter.RecipeViewHolder>(Companion) {

    class RecipeViewHolder(val binding: RecipeViewholderBinding): RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<Recipe>() {
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem == newItem
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem.title == newItem.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeViewholderBinding.inflate(LayoutInflater.from(parent.context))
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.binding.recipe = currentRecipe
        ImageLoader.loadRecipeImage(holder.binding.recipeImageView, currentRecipe.imageURL)
        holder.binding.invalidateAll()
    }

    override fun getItemCount() = recipeList.size
}
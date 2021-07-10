package com.africanbongo.whipitkotlin.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.africanbongo.spoonacularandroid.model.Recipe
import com.africanbongo.whipitkotlin.databinding.RecipeListItemBinding
import com.africanbongo.whipitkotlin.ui.fetchImage

/**
 * Recyclerview adapter that displays a list of [Recipe]s.
 * Use the [submitList] method to update the list shown.
 */
class RecipeListAdapter :
    ListAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(DiffCallback) {
    class RecipeViewHolder(private var binding: RecipeListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.recipe = recipe
            recipe.image?.let { binding.recipeImageView.fetchImage(it) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(RecipeListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }
}
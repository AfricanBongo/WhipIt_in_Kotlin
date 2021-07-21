package com.africanbongo.whipitkotlin.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import zw.co.bitpirates.spoonacularclient.model.Recipe
import com.africanbongo.whipitkotlin.databinding.RecipeListItemBinding
import com.africanbongo.whipitkotlin.domain.SummarisedRecipe
import com.africanbongo.whipitkotlin.ui.util.fetchImage

/**
 * Recyclerview adapter that displays a list of [Recipe]s.
 * Use the [submitList] method to update the list shown.
 */
class RecipeListAdapter :
    ListAdapter<SummarisedRecipe, RecipeListAdapter.RecipeViewHolder>(DiffCallback) {
    class RecipeViewHolder(private var binding: RecipeListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: SummarisedRecipe) {
            binding.recipe = recipe
            binding.recipeCardImage.fetchImage(recipe.imageUrl)
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<SummarisedRecipe>() {
        override fun areItemsTheSame(oldItem: SummarisedRecipe, newItem: SummarisedRecipe): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SummarisedRecipe, newItem: SummarisedRecipe): Boolean {
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
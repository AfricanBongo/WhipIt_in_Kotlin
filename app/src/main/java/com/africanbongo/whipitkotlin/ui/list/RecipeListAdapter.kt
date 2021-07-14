package com.africanbongo.whipitkotlin.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import zw.co.bitpirates.spoonacularclient.model.Recipe
import com.africanbongo.whipitkotlin.databinding.RecipeListItemBinding
import com.africanbongo.whipitkotlin.domain.DomainRecipe
import com.africanbongo.whipitkotlin.ui.fetchImage

/**
 * Recyclerview adapter that displays a list of [Recipe]s.
 * Use the [submitList] method to update the list shown.
 */
class RecipeListAdapter :
    ListAdapter<DomainRecipe, RecipeListAdapter.RecipeViewHolder>(DiffCallback) {
    class RecipeViewHolder(private var binding: RecipeListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: DomainRecipe) {
            binding.recipe = recipe
            recipe.imageUrl?.let { binding.recipeImageView.fetchImage(it) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<DomainRecipe>() {
        override fun areItemsTheSame(oldItem: DomainRecipe, newItem: DomainRecipe): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DomainRecipe, newItem: DomainRecipe): Boolean {
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
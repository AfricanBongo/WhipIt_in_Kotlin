package com.africanbongo.whipitkotlin.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.africanbongo.spoonacularandroid.model.Cuisine

/**
 * Database model of the network model [Cuisine].
 */
@Entity
data class DatabaseCuisine (
    @PrimaryKey(autoGenerate = false) val cuisineId: Int,
    val type: String
)
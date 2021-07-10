package com.africanbongo.whipitkotlin.storage.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.africanbongo.spoonacularandroid.model.CuisineEnum

/**
 * Database model of the network model [CuisineEnum].
 */
@Entity
data class DatabaseCuisine (
    @PrimaryKey(autoGenerate = false) val cuisineId: Int,
    val type: String
)
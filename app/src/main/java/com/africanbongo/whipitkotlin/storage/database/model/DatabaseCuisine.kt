package com.africanbongo.whipitkotlin.storage.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import zw.co.bitpirates.spoonacularclient.model.CuisineEnum

/**
 * Database model of the network model [CuisineEnum].
 */
@Entity(tableName = "cuisine_table")
data class DatabaseCuisine (
    @PrimaryKey(autoGenerate = false) val cuisineId: Int,
    val type: String
)
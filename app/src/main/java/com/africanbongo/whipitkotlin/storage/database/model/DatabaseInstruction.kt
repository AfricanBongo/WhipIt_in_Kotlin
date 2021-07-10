package com.africanbongo.whipitkotlin.storage.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.africanbongo.spoonacularandroid.model.Instruction

/**
 * Database model representation of the [Instruction] network model.
 */
@Entity(tableName = "instruction_table")
data class DatabaseInstruction (
    @PrimaryKey(autoGenerate = false) val recipeId: Int,
    val number: Int,
    val step: String
)
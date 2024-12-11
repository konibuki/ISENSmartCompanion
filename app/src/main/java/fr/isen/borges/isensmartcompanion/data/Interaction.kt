package fr.isen.borges.isensmartcompanion.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "interaction_table")
data class Interaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val response: String,
    val timestamp: Date = Date()
)
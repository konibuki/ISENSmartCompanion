package fr.isen.borges.isensmartcompanion.model


data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val category: String
)


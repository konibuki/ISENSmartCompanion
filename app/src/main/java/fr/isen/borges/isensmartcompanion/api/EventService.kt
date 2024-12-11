package fr.isen.borges.isensmartcompanion.api

import fr.isen.borges.isensmartcompanion.model.Event
import retrofit2.http.GET

interface EventService {
    @GET("events.json")
    suspend fun getEvents(): List<Event>
}
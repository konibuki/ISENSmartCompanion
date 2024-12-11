package fr.isen.borges.isensmartcompanion.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.borges.isensmartcompanion.model.Event
import kotlinx.coroutines.launch
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Call


interface EventService {
    @GET("events.json")
    fun getEvents(): List<Event>
}




val retrofit = Builder()
    .baseUrl("https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val eventService = retrofit.create(EventService::class.java)
val events = eventService.getEvents()








class EventViewModel : ViewModel() {

    private val eventRepository = EventRepository()

    // Variable pour stocker les événements récupérés
    var events = mutableListOf<Event>()

    // Fonction pour récupérer les événements
    fun loadEvents() {
        viewModelScope.launch {
            events = eventRepository.getEvents().toMutableList()
        }
    }
}
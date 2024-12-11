package fr.isen.borges.isensmartcompanion.ui.theme



import fr.isen.borges.isensmartcompanion.model.Event
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val eventService = retrofit.create(EventService::class.java)

    // Fonction pour récupérer les événements depuis l'API
    suspend fun getEvents(): List<Event> {
        return eventService.getEvents()
    }
}

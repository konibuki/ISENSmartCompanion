package fr.isen.borges.isensmartcompanion.api


import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private val retrofit = Builder()
        .baseUrl("https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val eventService: EventService by lazy {
        retrofit.create(EventService::class.java)
    }
}
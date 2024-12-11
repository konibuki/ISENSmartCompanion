package fr.isen.borges.isensmartcompanion.data

import kotlinx.coroutines.flow.Flow

class InteractionRepository(private val dao: InteractionDao) {
    val allInteractions: Flow<List<Interaction>> = dao.getAllInteractions()

    suspend fun insertInteraction(interaction: Interaction) {
        dao.insertInteraction(interaction)
    }

    suspend fun deleteInteraction(interaction: Interaction) {
        dao.deleteInteraction(interaction)
    }

    suspend fun deleteAllInteractions() {
        dao.deleteAllInteractions()
    }
}
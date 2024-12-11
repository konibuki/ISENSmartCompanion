package fr.isen.borges.isensmartcompanion.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InteractionDao {
    @Query("SELECT * FROM interaction_table ORDER BY timestamp DESC")
    fun getAllInteractions(): Flow<List<Interaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInteraction(interaction: Interaction)

    @Delete
    suspend fun deleteInteraction(interaction: Interaction)

    @Query("DELETE FROM interaction_table")
    suspend fun deleteAllInteractions()
}
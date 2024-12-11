package fr.isen.borges.isensmartcompanion.composant.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.borges.isensmartcompanion.data.Interaction
import fr.isen.borges.isensmartcompanion.data.InteractionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class InteractionViewModel(private val repository: InteractionRepository) : ViewModel() {
    val allInteractions: Flow<List<Interaction>> = repository.allInteractions

    fun insertInteraction(interaction: Interaction) = viewModelScope.launch {
        repository.insertInteraction(interaction)
    }

    fun deleteInteraction(interaction: Interaction) = viewModelScope.launch {
        repository.deleteInteraction(interaction)
    }

    fun deleteAllInteractions() = viewModelScope.launch {
        repository.deleteAllInteractions()
    }
}


@Composable
fun HistoryScreen(viewModel: InteractionViewModel) {
    val interactions by viewModel.allInteractions.collectAsState(initial = emptyList())

    LazyColumn {
        items(interactions) { interaction ->
            InteractionItem(
                interaction = interaction,
                onDelete = { viewModel.deleteInteraction(interaction) }
            )
        }
    }

    Button(
        onClick = { viewModel.deleteAllInteractions() },
        //modifier = Modifier.fillMaxWidth()
    ) {
        Text("Supprimer tout l'historique")
    }
}

@Composable
fun InteractionItem(interaction: Interaction, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Question : ${interaction.question}", style = MaterialTheme.typography.titleLarge)
            Text("Réponse : ${interaction.response}", style = MaterialTheme.typography.titleMedium)
            Text(
                "Date : ${interaction.timestamp}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
            IconButton(onClick = onDelete, modifier = Modifier.align(Alignment.End)) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    }
}
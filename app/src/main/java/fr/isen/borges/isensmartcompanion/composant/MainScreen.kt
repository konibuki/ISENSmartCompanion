package fr.isen.borges.isensmartcompanion.composant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig

import fr.isen.borges.isensmartcompanion.BuildConfig
import fr.isen.borges.isensmartcompanion.data.Interaction
import fr.isen.borges.isensmartcompanion.data.InteractionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


val model = GenerativeModel(
    modelName = "gemini-1.5-flash",
    apiKey = BuildConfig.API_KEY,
    generationConfig = generationConfig {
        temperature = 0.15f
        topK = 32
        topP = 1f
        maxOutputTokens = 4096
    }
)


@Composable
fun MainScreen(repository: InteractionRepository) {
    var question by remember { mutableStateOf("") }
    var itemList by remember { mutableStateOf(listOf<String>()) }
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        // verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "ISEN",
            fontSize = 70.sp,
            color = Color.Red
        )
        Text(
            text = "Smart Companion",
            fontSize = 15.sp
        )
        LazyColumn(
            Modifier
                .weight(1F)
                .fillMaxHeight()
        ) {
            items(itemList) { item ->
                // Chaque élément de la liste est affiché dans un Text
                Text(
                    text = item,
                    //style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        Box() {
            TextField(
                value = question, // Le texte actuel


                onValueChange = { question = it }, // Mise à jour du texte
                placeholder = { Text("Entrez du texte ici") }, // Placeholder
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp)// Remplir toute la largeur disponible
            )
            Button(
                onClick = {
                    if (question.isNotEmpty()) {
                        scope.launch {
                            val response = model.generateContent(
                                content {
                                    text(question)
                                }
                            )
                            itemList += (("Isen poto :") + response.text) ?: ""
                            CoroutineScope(Dispatchers.IO).launch {
                                val interaction =
                                    Interaction(question = question, response = response.text ?: "")
                                repository.insertInteraction(interaction)
                            }
                            question = ""
                        }
                    }

                },
                shape = RoundedCornerShape(12.dp), // Forme arrondie
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(60.dp)


            ) {
                Text(
                    text = "->",
                    color = Color.White // Couleur du texte

                )
            }
        }
    }
}
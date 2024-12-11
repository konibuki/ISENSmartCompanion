package fr.isen.borges.isensmartcompanion.composant.ui.theme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import fr.isen.borges.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val eventId = intent.getStringExtra("event_id") ?: "Événement inconnu"
        val eventTitle = intent.getStringExtra("event_Title") ?: "Événement inconnu"
        val eventDescription = intent.getStringExtra("event_description") ?: "Événement inconnu"
        val eventDate = intent.getStringExtra("event_date") ?: "Événement inconnu"
        val eventLocation = intent.getStringExtra("event_location") ?: "Événement inconnu"
        val eventCategory = intent.getStringExtra("event_category") ?: "Événement inconnu"

        setContent {
            ISENSmartCompanionTheme {
                EventDetailScreen(
                    eventId,
                    eventTitle,
                    eventDescription,
                    eventDate,
                    eventLocation,
                    eventCategory,
                )
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ISENSmartCompanionTheme {
        Greeting2("Android")
    }
}

@Composable
fun EventDetailScreen(
    eventId: String,
    eventTitle: String,
    eventDescription: String,
    eventDate: String,
    eventLocation: String,
    eventCategory: String
)
{
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
    val isNotified = remember { mutableStateOf(sharedPreferences.getBoolean("is_notified", false)) }


    // Charger la préférence de notification dans LaunchedEffect
    LaunchedEffect(eventId) {

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 250.dp)
    ) {
        Text(
            text = "ID : $eventId",
            modifier = Modifier
                .padding(0.dp, 15.dp)
        )
        Text(
            text = "Nom de l'évènement : $eventTitle",
            modifier = Modifier
                .padding(0.dp, 15.dp)
        )
        Text(
            text = "Description de l'évènement : $eventDescription",
            modifier = Modifier
                .padding(0.dp, 15.dp)
        )
        Text(
            text = "Date de l'évènement : $eventDate",
            modifier = Modifier
                .padding(0.dp, 15.dp)
        )
        Text(
            text = "Location de l'évènement : $eventLocation",
            modifier = Modifier
                .padding(0.dp, 15.dp)
        )
        Text(
            text = "Location de l'évènement : $eventCategory",
            modifier = Modifier
                .padding(0.dp, 15.dp)
        )
        IconButton(
            onClick = {
                isNotified.value = !isNotified.value
                sharedPreferences.edit().putBoolean("is_notified", isNotified.value).apply()

                if (isNotified.value) {
                    scheduleNotification(context)
                }
            }
        ) {
            Icon(
                imageVector = if (isNotified.value) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                contentDescription = "Notification"
            )
        }
    }

}

fun scheduleNotification(context: Context) {
    val isNotified = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        .getBoolean("is_notified", false)

    if (isNotified) {
        Log.d("EventDetailActivity", "Planification de la notification")
        // Planifier l'envoi de la notification après 10 secondes
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("EventDetailActivity", "Envoi de la notification")
            sendNotification(context)
        }, 10000) // 10 secondes en millisecondes
    }
}


fun sendNotification(context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "event_notifications",
            "Event Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, "event_notifications")
        .setContentTitle("Nouvel événement")
        .setContentText("Vous avez un événement à venir.")
        .setSmallIcon(android.R.drawable.ic_notification_overlay)
        .setAutoCancel(true)
        .build()}

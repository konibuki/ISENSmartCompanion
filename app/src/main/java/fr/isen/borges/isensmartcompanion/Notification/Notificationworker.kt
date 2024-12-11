package fr.isen.borges.isensmartcompanion.Notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import fr.isen.borges.isensmartcompanion.R

fun sendNotification(context: Context, title: String, message: String) {
    val builder = NotificationCompat.Builder(context, "isen_channel")
        .setSmallIcon(android.R.drawable.ic_notification_overlay) // Remplacez par votre icône
        .setContentTitle(title)
        .setContentText(message)}
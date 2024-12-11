package fr.isen.borges.isensmartcompanion


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.borges.isensmartcompanion.composant.EventListScreen
import fr.isen.borges.isensmartcompanion.composant.MainScreen
import fr.isen.borges.isensmartcompanion.composant.TabView
import fr.isen.borges.isensmartcompanion.composant.ui.theme.HistoryScreen
import fr.isen.borges.isensmartcompanion.composant.ui.theme.InteractionViewModel
import fr.isen.borges.isensmartcompanion.composant.ui.theme.InteractionViewModelFactory
import fr.isen.borges.isensmartcompanion.data.AppDatabase
import fr.isen.borges.isensmartcompanion.data.InteractionRepository
import fr.isen.borges.isensmartcompanion.model.TabBarItem
import fr.isen.borges.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import android.content.Context
import fr.isen.borges.isensmartcompanion.composant.EventDetailScreen
import fr.isen.borges.isensmartcompanion.model.Event


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            createNotificationChannel(this)
            EventDetailScreen(event = Event("1", "Sample Event", "This is a sample event", "2023-10-01","Paris" ,"Conference"))

            val db = AppDatabase.getDatabase(applicationContext)
            val repository = InteractionRepository(db.interactionDao())


            val homeTab = TabBarItem(
                title = "Accueil",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            )
            val alertsTab = TabBarItem(
                title = "événement",
                selectedIcon = Icons.Filled.Notifications,
                unselectedIcon = Icons.Outlined.Notifications,
                badgeAmount = 7
            )
            val settingsTab = TabBarItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings
            )
            val moreTab = TabBarItem(
                title = "Historique",
                selectedIcon = Icons.Filled.List,
                unselectedIcon = Icons.Outlined.List
            )

            // creating a list of all the tabs
            val tabBarItems = listOf(homeTab, alertsTab, settingsTab, moreTab)

            // creating our navController
            val navController = rememberNavController()
            ISENSmartCompanionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(bottomBar = { TabView(tabBarItems, navController) }) {
                        NavHost(navController = navController, startDestination = homeTab.title) {
                            composable(homeTab.title) {
                                MainScreen(repository)
                            }
                            composable(alertsTab.title) {
                                EventListScreen()

                            }
                            composable(settingsTab.title) {
                                Text(settingsTab.title)
                            }
                            composable(moreTab.title) {
                                val viewModel: InteractionViewModel = viewModel(factory = InteractionViewModelFactory(repository))
                                HistoryScreen(viewModel = viewModel)

                            }
                        }
                    }
                }
            }
        }
    }
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "ISEN Notifications"
        val descriptionText = "Canal pour les notifications ISEN"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("isen_channel", name, importance).apply {
            description = descriptionText
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        }
    }















package fr.isen.borges.isensmartcompanion.model

import androidx.compose.ui.graphics.vector.ImageVector

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

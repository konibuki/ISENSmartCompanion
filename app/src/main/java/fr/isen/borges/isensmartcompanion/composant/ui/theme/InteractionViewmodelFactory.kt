package fr.isen.borges.isensmartcompanion.composant.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.isen.borges.isensmartcompanion.data.InteractionRepository

class InteractionViewModelFactory(
    private val repository: InteractionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InteractionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InteractionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
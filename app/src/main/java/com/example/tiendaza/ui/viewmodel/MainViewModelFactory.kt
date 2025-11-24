
package com.example.tiendaza.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiendaza.data.repository.PublicacionRepositoryImpl

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            // ViewModel de la app, usando el repo real
            return MainViewModel(
                repository = PublicacionRepositoryImpl()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

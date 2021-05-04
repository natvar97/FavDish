package com.example.favdish.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favdish.model.database.FavDishRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class FavDishViewModelFactory(
    private val repository: FavDishRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavDishViewModel::class.java))
            return FavDishViewModel(repository) as T
        throw IllegalArgumentException("Unknown View Model class found")
    }

}
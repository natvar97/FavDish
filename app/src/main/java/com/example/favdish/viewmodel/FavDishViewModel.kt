package com.example.favdish.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.favdish.model.database.FavDishRepository
import com.example.favdish.model.entities.FavDish
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavDishViewModel @Inject constructor(
    private val repository: FavDishRepository
) : ViewModel() {

    fun insert(favDish: FavDish) = viewModelScope.launch {
        repository.insertFavDishDetails(favDish)
    }

    val allDishesList: LiveData<List<FavDish>> = repository.allDishesList.asLiveData()

    fun update(favDish: FavDish) = viewModelScope.launch {
        repository.updateFavDishDetails(favDish)
    }

    val allFavouriteDishesList: LiveData<List<FavDish>> = repository.allFavouriteDishes.asLiveData()


    fun delete(favDish: FavDish) = viewModelScope.launch {
        repository.delteFavDishDetails(favDish)
    }

    fun getFilteredDishesList(value: String): LiveData<List<FavDish>> =
        repository.getFilteredDishesList(value).asLiveData()
}
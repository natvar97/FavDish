package com.example.favdish.model.database

import androidx.annotation.WorkerThread
import com.example.favdish.model.entities.FavDish
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavDishRepository @Inject constructor(
    private val favDishDao: FavDishDao
) {

    @WorkerThread
    suspend fun insertFavDishDetails(favDish: FavDish) {
        favDishDao.insertFavDishDetails(favDish)
    }

    val allDishesList: Flow<List<FavDish>> = favDishDao.getAllDishesList()


    @WorkerThread
    suspend fun updateFavDishDetails(favDish: FavDish) {
        favDishDao.updateFavDishDetails(favDish)
    }

    val allFavouriteDishes: Flow<List<FavDish>> = favDishDao.getFavDishesList()

    @WorkerThread
    suspend fun delteFavDishDetails(favDish: FavDish) {
        favDishDao.deleteFavDishDetails(favDish)
    }

    fun getFilteredDishesList(value: String): Flow<List<FavDish>> =
        favDishDao.getFilteredDishesList(value)

}
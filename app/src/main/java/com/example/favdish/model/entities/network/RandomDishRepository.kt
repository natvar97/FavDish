package com.example.favdish.model.entities.network

import androidx.annotation.WorkerThread
import com.example.favdish.model.entities.RandomDish
import com.example.favdish.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RandomDishRepository @Inject constructor(private val randomDishAPI: RandomDishAPI) {


    @WorkerThread
    suspend fun getRandomDish() = randomDishAPI.getRandomDishes(
        Constants.API_KEY_VALUE,
        Constants.LIMIT_LICENSE_VALUE,
        Constants.TAGS_VALUE,
        Constants.NUMBER_VALUE
    )


}
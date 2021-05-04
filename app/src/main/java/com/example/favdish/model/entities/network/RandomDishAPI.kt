package com.example.favdish.model.entities.network

import com.example.favdish.model.entities.RandomDish
import com.example.favdish.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomDishAPI {

    @GET(Constants.API_ENDPOINT)
    fun getRandomDishes(
        @Query(Constants.API_KEY) apiKey: String,
        @Query(Constants.LIMIT_LICENSE) limitLicense: Boolean,
        @Query(Constants.TAGS) tags: String,
        @Query(Constants.NUMBER) number: Int
    ): Single<RandomDish.Recipes>

    /*
        here RandomDish.Recipes is the list of recipes which get data from api
        and give the random recipe or dish from api

        here we are using RxJava for api calling with Retrofit that is why we use Single class
     */
}
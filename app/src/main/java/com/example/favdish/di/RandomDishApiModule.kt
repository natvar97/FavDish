package com.example.favdish.di

import com.example.favdish.model.entities.network.RandomDishAPI
import com.example.favdish.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RandomDishApiModule {

    @Provides
    fun provideRandomDishApi(retrofit: Retrofit): RandomDishAPI {
        return retrofit.create(RandomDishAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())  //this is special for RxJava
            .build()
    }

}
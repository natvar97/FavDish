package com.example.favdish.di

import android.content.Context
import androidx.room.Room
import com.example.favdish.model.database.FavDishDao
import com.example.favdish.model.database.FavDishRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDao(favDishRoomDatabase: FavDishRoomDatabase): FavDishDao {
        return favDishRoomDatabase.favDishDao()
    }

    @Provides
    fun provideFavDishRoomDatabase(@ApplicationContext context: Context): FavDishRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavDishRoomDatabase::class.java,
            "fav_dish_database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}
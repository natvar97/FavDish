package com.example.favdish.application

import android.app.Application
import com.example.favdish.model.database.FavDishRepository
import com.example.favdish.model.database.FavDishRoomDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FavDishApplication : Application() {}
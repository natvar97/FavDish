package com.example.favdish.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.favdish.model.entities.FavDish

@Database(
    entities = [FavDish::class],
    version = 1
)
abstract class FavDishRoomDatabase : RoomDatabase() {

    abstract fun favDishDao() : FavDishDao

}
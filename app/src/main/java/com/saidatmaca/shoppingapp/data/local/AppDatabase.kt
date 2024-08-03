package com.saidatmaca.shoppingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saidatmaca.shoppingapp.domain.model.FavProductModel
import com.saidatmaca.shoppingapp.domain.model.ProductModel

@Database(entities = [ProductModel::class,FavProductModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val roomDao : RoomDatabaseDao

}
package com.saidatmaca.shoppingapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.saidatmaca.shoppingapp.core.common.Constants
import com.saidatmaca.shoppingapp.domain.model.FavProductModel
import com.saidatmaca.shoppingapp.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDatabaseDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserProductList(list: List<ProductModel>)

    @Update
    suspend fun updatePorductModel(model: ProductModel)

    @Query("SELECT * FROM ${Constants.ROOM_PRODUCT_TABLE}")
    suspend fun getAllProductList() : List<ProductModel>

    @Query("DELETE FROM ${Constants.ROOM_PRODUCT_TABLE}")
    suspend fun deleteProductList()

    @Query("SELECT * FROM ${Constants.ROOM_PRODUCT_TABLE}")
    fun getAllProductListLive(): Flow<List<ProductModel>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavProductList(list: List<FavProductModel>)

    @Update
    suspend fun updateFavProductModel(model: FavProductModel)

    @Query("SELECT * FROM ${Constants.ROOM_FAV_PRODUCT_TABLE}")
    suspend fun getAllFavProductList() : List<FavProductModel>

    @Query("DELETE FROM ${Constants.ROOM_FAV_PRODUCT_TABLE}")
    suspend fun deleteFavProductList()

    @Query("SELECT * FROM ${Constants.ROOM_FAV_PRODUCT_TABLE}")
    fun getAllFavProductListLive(): Flow<List<FavProductModel>>

}
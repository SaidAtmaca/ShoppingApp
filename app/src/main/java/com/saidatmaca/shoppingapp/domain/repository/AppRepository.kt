package com.saidatmaca.shoppingapp.domain.repository

import com.saidatmaca.shoppingapp.core.utils.Resource
import com.saidatmaca.shoppingapp.domain.model.FavProductModel
import com.saidatmaca.shoppingapp.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow


interface AppRepository {


    fun inserProductList(list: List<ProductModel>)

    fun updatePorductModel(model: ProductModel)

    fun getAllProductList(): Flow<List<ProductModel>>

    fun deleteProductList()

    fun getAllProductListLive(): Flow<List<ProductModel>>

    fun getProducts() : Flow<Resource<List<ProductModel>>>


    fun insertFavProductList(list: List<FavProductModel>)

    fun updateFavProductModel(model: FavProductModel)

    fun getAllFavProductList(): Flow<List<FavProductModel>>

    fun deleteFavProductList()

    fun getAllFavProductListLive(): Flow<List<FavProductModel>>

}
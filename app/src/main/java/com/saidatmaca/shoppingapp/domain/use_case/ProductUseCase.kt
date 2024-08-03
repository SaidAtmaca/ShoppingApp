package com.saidatmaca.shoppingapp.domain.use_case

import com.saidatmaca.shoppingapp.core.utils.Resource
import com.saidatmaca.shoppingapp.domain.model.FavProductModel
import com.saidatmaca.shoppingapp.domain.model.ProductModel
import com.saidatmaca.shoppingapp.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow


class ProductUseCase (
    private val repository : AppRepository
) {


    fun inserProductList(list: List<ProductModel>) =repository.inserProductList(list)

    fun updatePorductModel(model: ProductModel)= repository.updatePorductModel(model)

    fun getAllProductList(): Flow<List<ProductModel>> = repository.getAllProductList()

    val productFlow: Flow<List<ProductModel>> = repository.getAllProductListLive()

    fun deleteProductList() = repository.deleteProductList()

    fun getProductsFromApi() : Flow<Resource<List<ProductModel>>> = repository.getProducts()

    fun insertFavProductList(list: List<FavProductModel>) =repository.insertFavProductList(list)

    fun updateFavProductModel(model: FavProductModel)= repository.updateFavProductModel(model)

    fun getAllFavProductList(): Flow<List<FavProductModel>> = repository.getAllFavProductList()

    val favProductFlow: Flow<List<FavProductModel>> = repository.getAllFavProductListLive()

    fun deleteFavProductList() = repository.deleteFavProductList()

}

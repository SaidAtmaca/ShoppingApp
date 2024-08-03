package com.saidatmaca.shoppingapp.data.repository

import com.saidatmaca.shoppingapp.core.utils.Resource
import com.saidatmaca.shoppingapp.data.local.RoomDatabaseDao
import com.saidatmaca.shoppingapp.data.remote.APIService
import com.saidatmaca.shoppingapp.domain.model.FavProductModel
import com.saidatmaca.shoppingapp.domain.model.ProductModel
import com.saidatmaca.shoppingapp.domain.repository.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.IOException


class AppRepositoryImpl(
    private val apiService: APIService,
    private val dao : RoomDatabaseDao
): AppRepository {


    override fun inserProductList(list: List<ProductModel>) {
        CoroutineScope(Dispatchers.IO)
            .launch {
                dao.deleteProductList()
                dao.inserProductList(list)



            }
    }

    override fun updatePorductModel(model: ProductModel) {
        CoroutineScope(Dispatchers.IO)
            .launch {

                dao.updatePorductModel(model)



            }
    }

    override fun getAllProductList(): Flow<List<ProductModel>> = flow{
        try {

            val list = dao.getAllProductList()
            emit(list)


        }catch(e: IOException) {
            e.printStackTrace()
        }
    }

    override fun deleteProductList() {
        CoroutineScope(Dispatchers.IO)
            .launch {

                dao.deleteProductList()
            }
    }

    override fun getAllProductListLive(): Flow<List<ProductModel>> {
        return dao.getAllProductListLive()
    }

    override fun getProducts(): Flow<Resource<List<ProductModel>>> = flow{
       emit(Resource.Loading())

        try {

            val response = apiService.getproducts()

            emit(Resource.Success(response))




        }catch(e: IOException) {

            emit(
                Resource.Error(
                    message = "Lütfen İnternet Bağlantınızı Kontrol Ediniz"
                ))
        }
    }

    override fun insertFavProductList(list: List<FavProductModel>) {
        CoroutineScope(Dispatchers.IO)
            .launch {
                dao.deleteFavProductList()
                dao.insertFavProductList(list)



            }
    }

    override fun updateFavProductModel(model: FavProductModel) {
        CoroutineScope(Dispatchers.IO)
            .launch {

                dao.updateFavProductModel(model)



            }
    }

    override fun getAllFavProductList(): Flow<List<FavProductModel>> = flow{
        try {

            val list = dao.getAllFavProductList()
            emit(list)


        }catch(e: IOException) {
            e.printStackTrace()
        }
    }

    override fun deleteFavProductList() {
        CoroutineScope(Dispatchers.IO)
            .launch {

                dao.deleteFavProductList()
            }
    }

    override fun getAllFavProductListLive(): Flow<List<FavProductModel>> {
        return dao.getAllFavProductListLive()
    }


}
package com.saidatmaca.shoppingapp.data.remote

import com.saidatmaca.shoppingapp.domain.model.ProductModel
import retrofit2.http.GET

interface APIService {


    @GET("products")
    suspend fun getproducts(): List<ProductModel>


}
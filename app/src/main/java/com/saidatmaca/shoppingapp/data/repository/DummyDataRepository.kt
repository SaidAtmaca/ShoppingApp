package com.saidatmaca.shoppingapp.data.repository

import com.saidatmaca.shoppingapp.domain.model.ProductModel

object DummyDataRepository {

 

    val dummyProduct = ProductModel(
        createdAt = "date",
        name = "Dummy Product",
        image = "https://loremflickr.com/640/480/food",
        price = "100.00",
        description = "This is a dummy product for testing purposes. It has a detailed description to simulate a real product.",
        model = "Model X",
        brand = "Brand Y",
        id = "123456"
    )
}
package com.saidatmaca.shoppingapp.presentation.ui.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.saidatmaca.shoppingapp.core.common.GlobalValues
import com.saidatmaca.shoppingapp.core.common.enums.UIEvent
import com.saidatmaca.shoppingapp.core.common.observeFavProductList
import com.saidatmaca.shoppingapp.core.common.observeProductList
import com.saidatmaca.shoppingapp.core.utils.Resource
import com.saidatmaca.shoppingapp.domain.model.FavProductModel
import com.saidatmaca.shoppingapp.domain.model.ProductModel
import com.saidatmaca.shoppingapp.domain.use_case.ProductUseCase
import com.saidatmaca.shoppingapp.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val productUseCase: ProductUseCase,
) : ViewModel(){

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    val productList : SnapshotStateList<ProductModel> = mutableStateListOf()
    val favProductList : SnapshotStateList<FavProductModel> = mutableStateListOf()
    val cartProductList : SnapshotStateList<ProductModel> = mutableStateListOf()


    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    fun setSearch(string: String){
        _searchText.value = string
    }

    private val _selectedIndex = mutableStateOf(0)
    val selectedIndex: State<Int> = _selectedIndex

    fun setSelectedIndex(int: Int){
        _selectedIndex.value = int
    }

    private var job: Job? = null



    fun goToCartScreen(navController: NavController){
        viewModelScope.launch {
            _eventFlow.emit(UIEvent.Navigate(Screen.CartScreen.route))
        }

    }

    fun getProductsFromApi(){

        job=viewModelScope.launch {
            productUseCase.getProductsFromApi()
                .onEach {result->
                    when (result) {

                        is Resource.Success -> {
                            GlobalValues.showLoading.postValue(false)


                            result.data?.let {
                                productList.clear()
                                productList.addAll(it)
                            }

                            Log.e("productApiResponse",result.data.toString())




                        }

                        is Resource.Error -> {
                            GlobalValues.showLoading.postValue(false)
                            _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            GlobalValues.showLoading.postValue(true)
                        }
                    }
                }.launchIn(this)
        }

    }



    fun goToDetailScreen(navController:NavController,model: ProductModel){
        viewModelScope.launch {
            navController.currentBackStackEntry?.savedStateHandle?.set("model",model)
            _eventFlow.emit(UIEvent.Navigate(Screen.DetailScreen.route))
        }
    }

    fun changeFavPosition(boolean: Boolean,model: ProductModel){

        viewModelScope.launch {
            productUseCase.getAllFavProductList()
                .collect(){ favList->
                    var tempList = arrayListOf<FavProductModel>()
                    tempList.addAll(favList)
                    val filteredList = favList.filter { it.id == model.id }
                    if (!boolean){


                        if (filteredList.isNotEmpty()){

                            tempList.remove(filteredList.first())

                            productUseCase.insertFavProductList(tempList)
                        }
                    }else{

                        if (filteredList.isEmpty()){

                            val tempModel = FavProductModel(
                                createdAt = model.createdAt,
                                name = model.name,
                                image = model.image,
                                price = model.price,
                                description = model.description,
                                model = model.model,
                                brand = model.brand,
                                id =model.id
                            )
                            tempList.addAll(favList)
                            tempList.add(tempModel)

                            productUseCase.insertFavProductList(tempList)
                        }

                    }


                }
        }
    }

    fun changeCartPosition(boolean: Boolean,model: ProductModel){

        viewModelScope.launch {
            productUseCase.getAllProductList()
                .collect(){ favList->
                    var tempList = arrayListOf<ProductModel>()
                    tempList.addAll(favList)
                    val filteredList = favList.filter { it.id == model.id }
                    if (!boolean){


                        if (filteredList.isNotEmpty()){

                            tempList.remove(filteredList.first())

                            productUseCase.inserProductList(tempList)
                        }
                    }else{

                        if (filteredList.isEmpty()){


                            tempList.addAll(favList)
                            tempList.add(model)

                            productUseCase.inserProductList(tempList)
                        }

                    }


                }
        }
    }


   init {

       getProductsFromApi()
       this.observeProductList(productUseCase){ list->

           Log.e("productListLog1",list.toString())
            cartProductList.clear()
            cartProductList.addAll(list)
       }

       this.observeFavProductList(productUseCase){ list ->

           favProductList.clear()
           favProductList.addAll(list)

       }

   }

}
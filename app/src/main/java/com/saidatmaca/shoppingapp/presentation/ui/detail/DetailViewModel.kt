package com.saidatmaca.shoppingapp.presentation.ui.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saidatmaca.shoppingapp.core.common.enums.UIEvent
import com.saidatmaca.shoppingapp.core.common.observeFavProductList
import com.saidatmaca.shoppingapp.core.common.observeProductList
import com.saidatmaca.shoppingapp.domain.model.FavProductModel
import com.saidatmaca.shoppingapp.domain.model.ProductModel
import com.saidatmaca.shoppingapp.domain.use_case.ProductUseCase
import com.saidatmaca.shoppingapp.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
) : ViewModel(){


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _selectedIndex = mutableStateOf(1)
    val selectedIndex: State<Int> = _selectedIndex

    fun setSelectedIndex(int: Int){
        _selectedIndex.value = int
    }

    val favProductList : SnapshotStateList<FavProductModel> = mutableStateListOf()
    val cartProductList : SnapshotStateList<ProductModel> = mutableStateListOf()



    private var job: Job? = null

    fun goToHomeScreen(){
        viewModelScope.launch {
            _eventFlow.emit(UIEvent.Navigate(Screen.HomeScreen.route))

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
        this.observeFavProductList(productUseCase){ list ->

            favProductList.clear()
            favProductList.addAll(list)

        }
        this.observeProductList(productUseCase){ list->

            Log.e("productListLog1",list.toString())
            cartProductList.clear()
            cartProductList.addAll(list)
        }
    }


















    init {


    }

}
package com.saidatmaca.shoppingapp.presentation.ui.cart

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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
) : ViewModel(){

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _selectedIndex = mutableStateOf(2)
    val selectedIndex: State<Int> = _selectedIndex

    fun setSelectedIndex(int: Int){
        _selectedIndex.value = int
    }

    private val _totalPrice = mutableStateOf(0.0)
    val totalPrice: State<Double> = _totalPrice

    fun setTotalPrice(double: Double){
        _totalPrice.value = double
    }

    val favProductList : SnapshotStateList<FavProductModel> = mutableStateListOf()
    val cartProductList : SnapshotStateList<ProductModel> = mutableStateListOf()

    fun goToHomeScreen(){
        viewModelScope.launch {
            _eventFlow.emit(UIEvent.Navigate(Screen.HomeScreen.route))

        }
    }


    init {
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
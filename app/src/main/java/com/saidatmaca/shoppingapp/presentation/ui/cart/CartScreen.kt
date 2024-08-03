package com.saidatmaca.shoppingapp.presentation.ui.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saidatmaca.shoppingapp.R
import com.saidatmaca.shoppingapp.core.common.enums.UIEvent
import com.saidatmaca.shoppingapp.presentation.components.AppTopBar
import com.saidatmaca.shoppingapp.presentation.components.BottomBar
import com.saidatmaca.shoppingapp.presentation.components.CartComponent
import com.saidatmaca.shoppingapp.presentation.ui.home.NoDataLayout
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartScreenViewModel = hiltViewModel()
) {


    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    navController.navigate(event.route)
                }


                else -> {}
            }
        }

    }

    Scaffold(
        topBar = {

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                AppTopBar(title = stringResource(id = R.string.eKasa),
                    isMainScreen = true
                ) {

                }



            }

        },
        bottomBar = {

            BottomBar(clicked = {
                viewModel.setSelectedIndex(it)
                if (viewModel.selectedIndex.value == 0){
                    viewModel.goToHomeScreen()
                }
            }, selectedIndex = viewModel.selectedIndex.value,
                favCount = viewModel.favProductList.size,
                cartCount = viewModel.cartProductList.size)
        }
    ){

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)){

            Column(Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally){

                AnimatedVisibility(visible = viewModel.cartProductList.isEmpty()) {

                    NoDataLayout()
                }

                AnimatedVisibility(visible = viewModel.cartProductList.isNotEmpty()) {

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        items(viewModel.cartProductList){

                            CartComponent(model = it,
                                defaultCount =1 )
                        }

                    }
                }
            }
        }
    }


}
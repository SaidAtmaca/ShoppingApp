package com.saidatmaca.shoppingapp.presentation.ui.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saidatmaca.shoppingapp.R
import com.saidatmaca.shoppingapp.core.common.enums.UIEvent
import com.saidatmaca.shoppingapp.presentation.components.AppTopBar
import com.saidatmaca.shoppingapp.presentation.components.BottomBar
import com.saidatmaca.shoppingapp.presentation.components.CartComponent
import com.saidatmaca.shoppingapp.presentation.ui.home.NoDataLayout
import com.saidatmaca.shoppingapp.presentation.ui.theme.FigmaColors
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

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(intrinsicSize = IntrinsicSize.Max)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = stringResource(id = R.string.price),
                                color = FigmaColors.primary1,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold)

                            Text(text = "$${viewModel.totalPrice.value}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = {
                        },
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(containerColor =  FigmaColors.primary1 ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp),
                            contentPadding = PaddingValues(2.dp)
                        ) {

                            Text(text =  stringResource(id = R.string.complete), color = Color.White, maxLines = 1)
                        }
                    }
                }


                BottomBar(clicked = {
                    viewModel.setSelectedIndex(it)
                    if (viewModel.selectedIndex.value == 0){
                        viewModel.goToHomeScreen()
                    }
                }, selectedIndex = viewModel.selectedIndex.value,
                    favCount = viewModel.favProductList.size,
                    cartCount = viewModel.cartProductList.size)
            }

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
                                defaultCount =1,
                                currentPrice = {
                                    var totalPrice = viewModel.totalPrice.value
                                    totalPrice+=it
                                    viewModel.setTotalPrice(totalPrice)
                                })
                        }

                    }
                }
            }
        }
    }


}
package com.saidatmaca.shoppingapp.presentation.ui.home

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.saidatmaca.shoppingapp.R
import com.saidatmaca.shoppingapp.core.common.enums.UIEvent
import com.saidatmaca.shoppingapp.presentation.components.AppTopBar
import com.saidatmaca.shoppingapp.presentation.components.BottomBar
import com.saidatmaca.shoppingapp.presentation.components.ProductCard
import kotlinx.coroutines.flow.collectLatest

@Preview(showBackground = true)
@Composable
fun Prevvv() {

}

@Composable
fun HomeScreen(navController: NavController,
               viewModel: HomeViewModel = hiltViewModel()) {

    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = remember { context as? ComponentActivity }




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



    AnimatedVisibility(visible = showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { activity?.finish() }) {
                    Text(stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(id = R.string.no))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.warning), fontSize = 20.sp)
            },
            text = {
                Text(stringResource(id = R.string.areYouSure))
            }
        )
    }





    BackHandler {
        showDialog=true
    }


    Scaffold(
        topBar = {

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                AppTopBar(title = stringResource(id = R.string.eMarket),
                    isMainScreen = true
                ) {

                }

                OutlinedTextField(value = viewModel.searchText.value,
                    onValueChange ={
                        viewModel.setSearch(it)
                    } ,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search,
                            contentDescription = "")
                    },
                    label = {
                            Text(text = stringResource(id = R.string.arama))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp))


            }

        },
        bottomBar = {

            BottomBar(clicked = {
                                viewModel.setSelectedIndex(it)
                if (viewModel.selectedIndex.value==2){
                    viewModel.goToCartScreen(navController)
                }
            }, selectedIndex = viewModel.selectedIndex.value,
                favCount = viewModel.favProductList.size,
                cartCount = viewModel.cartProductList.size)
        }
    ) {

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)){

            Column(Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                
                AnimatedVisibility(visible = viewModel.productList.isNotEmpty()) {
                    LazyVerticalGrid(columns = GridCells.Fixed(2))
                    {

                        items(if (viewModel.searchText.value.isEmpty())viewModel.productList else viewModel.productList.filter { it.name.contains(viewModel.searchText.value, ignoreCase = true) }){ product->

                            val isFav = if (
                                viewModel.favProductList.filter { it.id ==  product.id}.isNotEmpty()
                            ) {
                                true
                            }else{
                                false
                            }

                            val isCart = if (
                                viewModel.cartProductList.filter { it.id ==  product.id}.isNotEmpty()
                            ) {
                                true
                            }else{
                                false
                            }

                            ProductCard(model = product,
                                isFavProduct =isFav,
                                isCartProduct = isCart,
                                favClicked = {
                                    viewModel.changeFavPosition(it,product)
                                },
                                addToCardClicked = {
                                    viewModel.changeCartPosition(it,product)
                                },
                                detailClicked = {
                                    viewModel.goToDetailScreen(navController,it)
                                })

                        }
                    } 
                }
                
                AnimatedVisibility(visible = viewModel.productList.isEmpty()) {

                    NoDataLayout()
                }
                


            }
        }

    }


    }



@Composable
fun NoDataLayout() {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.lotiie_empty
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    Column(
        Modifier
            .fillMaxWidth()
            .height(200.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {



        LottieAnimation(composition = preloaderLottieComposition,
            progress = {
                preloaderProgress
            },
            modifier = Modifier.size(100.dp))

        Text(text = stringResource(id = R.string.dataNotfound))
    }
}




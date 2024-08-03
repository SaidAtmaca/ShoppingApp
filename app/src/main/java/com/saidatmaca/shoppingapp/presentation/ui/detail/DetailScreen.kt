package com.saidatmaca.shoppingapp.presentation.ui.detail

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.saidatmaca.shoppingapp.R
import com.saidatmaca.shoppingapp.core.common.enums.UIEvent
import com.saidatmaca.shoppingapp.domain.model.ProductModel
import com.saidatmaca.shoppingapp.presentation.components.AppTopBar
import com.saidatmaca.shoppingapp.presentation.ui.theme.FigmaColors
import com.saidatmaca.shoppingapp.presentation.util.Screen
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {


}

@Composable
fun DetailScreen(
    navController: NavController,
    model: ProductModel?,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var backPressHandled by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(model?.image)
            .placeholder(R.drawable.loading_gifo)
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )



    BackHandler(enabled = !backPressHandled) {

        navController.navigate(Screen.HomeScreen.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = false
            }
        }

        backPressHandled = true
        coroutineScope.launch {
            awaitFrame()
            onBackPressedDispatcher?.onBackPressed()
            backPressHandled = false
        }
    }



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
        modifier = Modifier.fillMaxSize(),
        topBar = {

            Column(Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End) {
                AppTopBar(title =model?.name ?: stringResource(id = R.string.detay),
                    isMainScreen = false
                ) {

                    viewModel.goToHomeScreen()
                }
            }



        },
        bottomBar = {

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
                        
                        Text(text = "$${model?.price ?: "0"}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
                
                val isCartProduct = viewModel.cartProductList.filter { it.id == model?.id }.isNotEmpty()

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { 
                        model?.let {

                            viewModel.changeCartPosition(!isCartProduct,model)
                        }
                                     },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (!isCartProduct)FigmaColors.primary1 else FigmaColors.redDark),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        contentPadding = PaddingValues(2.dp)
                    ) {

                        Text(text = if (!isCartProduct) stringResource(id = R.string.addToCard) else stringResource(
                            id = R.string.discard
                        ), color = Color.White, maxLines = 1)
                    }
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)){


            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Max)
                    .padding(10.dp),
                    contentAlignment = Alignment.TopEnd){

                    Image(painter =painter ,
                        contentDescription ="",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .zIndex(0f))


                    val isFavProduct = viewModel.favProductList.filter { it.id == model?.id }.isNotEmpty()
                    if(isFavProduct) {
                        Icon(imageVector = Icons.Default.Star,
                            contentDescription ="",
                            modifier = Modifier
                                .size(36.dp)
                                .zIndex(0.1f)
                                .clickable {
                                    model?.let {

                                        viewModel.changeFavPosition(false, it)
                                    }
                                },
                            tint = FigmaColors.orangeDark
                        )
                    }else{

                        Image(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription ="",
                            modifier = Modifier
                                .size(36.dp)
                                .zIndex(0.1f)
                                .clickable {
                                    model?.let {

                                        viewModel.changeFavPosition(true, it)
                                    }
                                })

                    }
                }



                Text(text = model?.description ?: "", color = Color.Black, fontSize = 12.sp,
                    maxLines = 6, softWrap = true, overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(10.dp))
                
            }


        }

    }
}


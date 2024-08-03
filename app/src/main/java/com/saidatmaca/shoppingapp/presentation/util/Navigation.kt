package com.saidatmaca.shoppingapp.presentation.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saidatmaca.shoppingapp.domain.model.ProductModel
import com.saidatmaca.shoppingapp.presentation.ui.cart.CartScreen
import com.saidatmaca.shoppingapp.presentation.ui.detail.DetailScreen
import com.saidatmaca.shoppingapp.presentation.ui.home.HomeScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route,
        modifier = Modifier.fillMaxSize()){



        composable(Screen.HomeScreen.route){
            HomeScreen(navController = navController,)
        }

        composable(Screen.DetailScreen.route){
            val model = navController.previousBackStackEntry?.savedStateHandle?.get<ProductModel>("model")
            DetailScreen(navController = navController, model = model)
        }

        composable(Screen.CartScreen.route){
            CartScreen(navController = navController)
        }

    }
}
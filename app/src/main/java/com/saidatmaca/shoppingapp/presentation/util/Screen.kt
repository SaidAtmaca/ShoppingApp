package com.saidatmaca.shoppingapp.presentation.util

sealed class Screen(val route : String){

    object HomeScreen : Screen("home_screen")

    object DetailScreen : Screen("detail_screen")

    object CartScreen : Screen("cart_screen")
}

package com.saidatmaca.shoppingapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.saidatmaca.shoppingapp.presentation.ui.theme.ShoppingAppTheme
import com.saidatmaca.shoppingapp.presentation.util.Navigation

@Composable
fun ShoppingApp(
) {
    ShoppingAppTheme(dynamicColor = false) {
        val navController = rememberNavController()


        Scaffold(Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(it)){
                Navigation(navController = navController )
            }
        }




    }
}

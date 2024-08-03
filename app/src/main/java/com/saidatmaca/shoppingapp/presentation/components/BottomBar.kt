package com.saidatmaca.shoppingapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saidatmaca.shoppingapp.presentation.ui.theme.FigmaColors

@Preview
@Composable
private fun Preview() {

    BottomBar(clicked = {}, selectedIndex = 0, favCount = 3, cartCount = 1)
}


@Composable
fun BottomBar(
    clicked:(Int)->Unit,
    selectedIndex : Int,
    favCount:Int,
    cartCount:Int
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(0.5.dp,FigmaColors.darkGrey)
    ) {

        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ){
            
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clickable {
                        clicked(0)
                    }
                    .background(if (selectedIndex == 0) FigmaColors.darkGrey else Color.White)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                
                Icon(imageVector = Icons.Outlined.Home, 
                    contentDescription ="",
                    tint = if (selectedIndex == 0) FigmaColors.primary1 else FigmaColors.darkGrey)
            }

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clickable {
                        clicked(1)
                    }
                    .background(if (selectedIndex == 1) FigmaColors.darkGrey else Color.White)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(imageVector = Icons.Outlined.Star,
                    contentDescription ="",
                    tint = if (selectedIndex == 1) FigmaColors.primary1 else FigmaColors.darkGrey)

                AnimatedVisibility(visible = favCount != 0) {

                    Text(text = "$favCount", color = if (selectedIndex == 1) FigmaColors.primary1 else FigmaColors.darkGrey, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp))
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clickable {
                        clicked(2)
                    }
                    .background(if (selectedIndex == 2) FigmaColors.darkGrey else Color.White)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription ="",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp),
                    tint = if (selectedIndex == 2) FigmaColors.primary1 else FigmaColors.darkGrey)

                AnimatedVisibility(visible = cartCount != 0) {

                    Text(text = "$cartCount", color = if (selectedIndex == 2) FigmaColors.primary1 else FigmaColors.darkGrey, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp))
                }


            }
            
            
            
            
        }
    }
}
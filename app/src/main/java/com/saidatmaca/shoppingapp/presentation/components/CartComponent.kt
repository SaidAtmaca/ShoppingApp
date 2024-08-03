package com.saidatmaca.shoppingapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saidatmaca.shoppingapp.data.repository.DummyDataRepository
import com.saidatmaca.shoppingapp.domain.model.ProductModel
import com.saidatmaca.shoppingapp.presentation.ui.theme.FigmaColors

@Preview
@Composable
private fun CartCompPreview() {

    CartComponent(model = DummyDataRepository.dummyProduct,
        defaultCount =0,
        currentPrice = {

        })
}


@Composable
fun CartComponent(
    model: ProductModel,
    defaultCount : Int,
    currentPrice:(Double) ->Unit

) {

    var count by remember {
        mutableStateOf(0)
    }

    var priceValue by remember {
        mutableStateOf(0.0)
    }

    LaunchedEffect(key1 = Unit) {
        count = defaultCount
        try {

            priceValue = model.price.toDouble()
            priceValue *= count
            currentPrice(priceValue)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .height(intrinsicSize = IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically) {

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(0.6f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = model.brand, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "$$priceValue", fontSize = 16.sp, fontWeight = FontWeight.Normal)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(0.4f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {


            Card(onClick = {
                if (count>=1){
                    count -= 1
                    priceValue = model.price.toDouble()*count
                    currentPrice(priceValue)
                }

            },
                enabled = count>0,
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(0.5.dp,FigmaColors.darkGrey),
                modifier = Modifier
                    .width(intrinsicSize = IntrinsicSize.Max)
                    .height(intrinsicSize = IntrinsicSize.Max)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(text = "-", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(12.dp))
                }
            }

            Card(onClick = {

            },
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(containerColor = FigmaColors.primary1),
                modifier = Modifier
                    .width(intrinsicSize = IntrinsicSize.Max)
                    .height(intrinsicSize = IntrinsicSize.Max)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(text = "$count", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(12.dp), color = Color.White)
                }
            }

            Card(onClick = {
                count += 1
                priceValue =model.price.toDouble()*count
                currentPrice(priceValue)
            },
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(0.5.dp,FigmaColors.darkGrey),
                modifier = Modifier
                    .width(intrinsicSize = IntrinsicSize.Max)
                    .height(intrinsicSize = IntrinsicSize.Max)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(text = "+", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp))
                }
            }

        }

    }

}
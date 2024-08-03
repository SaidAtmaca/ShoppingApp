package com.saidatmaca.shoppingapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.saidatmaca.shoppingapp.R
import com.saidatmaca.shoppingapp.data.repository.DummyDataRepository
import com.saidatmaca.shoppingapp.domain.model.ProductModel
import com.saidatmaca.shoppingapp.presentation.ui.theme.FigmaColors

@Preview
@Composable
private fun ProductCardPreview() {

    ProductCard(model = DummyDataRepository.dummyProduct,isFavProduct = false, favClicked = {},
        isCartProduct = false, addToCardClicked = {}, detailClicked = {})
}


@Composable
fun ProductCard(
    model: ProductModel,
    isFavProduct:Boolean,
    isCartProduct:Boolean,
    addToCardClicked :(Boolean)->Unit,
    favClicked :(Boolean)->Unit,
    detailClicked:(ProductModel)->Unit
) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(model.image)
            .placeholder(R.drawable.loading_gifo)
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )

    val favIcon = if (isFavProduct) Icons.Default.Star else Icons.Outlined.Star


    Card(
        modifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Max)
            .width(intrinsicSize = IntrinsicSize.Max)
            .padding(10.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(0.5.dp,FigmaColors.darkGrey)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Max), contentAlignment = Alignment.TopEnd){


                Image(painter = painter,
                    contentDescription ="",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            detailClicked(model)
                        }
                        .zIndex(0f),
                    contentScale = ContentScale.Crop)


                if(isFavProduct) {
                    Icon(imageVector = Icons.Default.Star,
                        contentDescription ="",
                        modifier = Modifier
                            .size(36.dp)
                            .zIndex(0.1f)
                            .clickable {
                                favClicked(!isFavProduct)
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
                                favClicked(!isFavProduct)
                            })

                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.Start
            ) {

                Text(text = "$"+model.price, color = FigmaColors.primary1, fontSize = 14.sp)

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.Start
            ) {

                Text(text = model.model, fontSize = 16.sp)

            }

            Button(onClick = { addToCardClicked(!isCartProduct) },
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
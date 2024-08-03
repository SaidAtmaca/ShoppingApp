package com.saidatmaca.shoppingapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saidatmaca.shoppingapp.presentation.ui.theme.SpaceMedium
import com.saidatmaca.shoppingapp.presentation.ui.theme.mainColorPalette

@Composable
fun AppTopBar(
    title:String,
    isMainScreen : Boolean,
    backClicked : ()->Unit
) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Max)
        .background(mainColorPalette.tone5)){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMedium),
            verticalAlignment = CenterVertically
        ){


            AnimatedVisibility(visible = !isMainScreen) {

                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription ="",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                        .clickable {
                            backClicked()
                        })
            }



            Text(text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color= mainColorPalette.tone4,
                modifier = Modifier.padding(horizontal = SpaceMedium))


        }

    }


}


@Composable
@Preview
fun AppTopBarPreview() {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White))
    AppTopBar(
        title = "Information",
        isMainScreen = false,
        backClicked = {}
    )
}
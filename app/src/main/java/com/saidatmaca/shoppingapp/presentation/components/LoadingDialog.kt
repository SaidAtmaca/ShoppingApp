package com.saidatmaca.shoppingapp.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.saidatmaca.shoppingapp.R


@Composable
fun LoadingDialog(
    onDismissRequest : () ->Unit
) {

    Dialog(onDismissRequest = {onDismissRequest()
    },
        properties = DialogProperties(dismissOnBackPress = true,
            dismissOnClickOutside = false,
            decorFitsSystemWindows = true,
            usePlatformDefaultWidth = false)
    ){

        val context = LocalContext.current

        val preloaderLottieComposition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                R.raw.loading_anim
            )
        )

        val preloaderProgress by animateLottieCompositionAsState(
            preloaderLottieComposition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true
        )

        LottieAnimation(composition = preloaderLottieComposition,
            progress = {
                preloaderProgress
            },
            modifier = Modifier.fillMaxSize())

    }
}



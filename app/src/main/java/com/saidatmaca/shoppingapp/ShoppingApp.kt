package com.saidatmaca.shoppingapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShoppingApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
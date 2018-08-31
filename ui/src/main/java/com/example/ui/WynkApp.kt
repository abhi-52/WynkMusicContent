package com.example.ui

import android.app.Application
import com.example.wynkbasic.content.ContentSDK

class WynkApp : Application() {

    lateinit var contentSDK : ContentSDK

    companion object {
        lateinit var instance: WynkApp
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        contentSDK = ContentSDK.getInstance(this)
    }
}
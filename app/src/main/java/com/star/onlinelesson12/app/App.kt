package com.star.onlinelesson12.app

import android.app.Application
import com.star.onlinelesson11.data.LocalStorage

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
        LocalStorage.init(this)
    }

    companion object{
        lateinit var instance : App
    }
}
package com.company.dilnoza.mvvm_hilt.app

import android.app.Application
import com.company.dilnoza.mvvm_hilt.room.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App :Application(){
    override fun onCreate() {
        super.onCreate()
       instance =this
        AppDatabase.init(this)
    }
    companion object{
        lateinit var instance: App
    }
}
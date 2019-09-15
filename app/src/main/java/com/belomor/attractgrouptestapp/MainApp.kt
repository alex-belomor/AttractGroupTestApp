package com.belomor.attractgrouptestapp

import android.app.Application
import com.belomor.attractgrouptestapp.network.ServiceAPI
import com.belomor.attractgrouptestapp.utils.Utils

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceAPI.init()
        Utils.init(this)
    }
}